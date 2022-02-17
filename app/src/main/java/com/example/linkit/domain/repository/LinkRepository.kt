package com.example.linkit.domain.repository

import android.graphics.Bitmap
import com.example.linkit.data.network.api.ILinkApi
import com.example.linkit.domain.repository.mapper.LinkMappers
import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.data.room.entity.LinkTagRef
import com.example.linkit.data.room.entity.LinkWithTags
import com.example.linkit.data.room.entity.TagEntity
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.Url
import com.example.linkit.domain.model.log
import com.example.linkit.domain.repository.mapper.BitmapMappers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 도메인 모델 Link를 담당하는 Repository
 */
class LinkRepository @Inject constructor(
    private val linkApi: ILinkApi,
    private val linkDao: LinkDao,
    private val linkMapper: LinkMappers,
    private val bitmapMapper: BitmapMappers,
) {
    /** Flow를 IO 쓰레드에서 동작시키고, emit과 collector를 다른 코루틴에서 실행시킨다 (conflate) */
    fun getAllLinks() : Flow<List<LinkWithTags>> {
        return linkDao.getLinks()
            .flowOn(Dispatchers.IO)
            .conflate()
    }

    fun getLinksInFolder(folderId: Long) : Flow<List<ILink>> {
        return linkDao.getLinksInFolder(folderId)
            .flowOn(Dispatchers.IO)
            .conflate()
            .map { linkMapper.map(it) }
    }

    suspend fun getLink(linkId: Long) : ILink {
        val entity =  linkDao.getLinkById(linkId)
        return linkMapper.map(entity)
    }

    /** 링크가 주어지면 이미지를 웹에서 불러오고, 그후 Room에 저장한다. */
    suspend fun addLink(link: ILink) {
        link.favicon = getFavicon(link.url)
        link.image = getImage(link.url)
        val entity = linkMapper.map(link)
        val linkId = linkDao.insert(entity.link)
        // 태그 및 다대다 테이블 insert
        for (tag in entity.tags) {
            linkDao.insert(tag)
            linkDao.insert(LinkTagRef(linkId = linkId, tag = tag.name))
        }
    }

    suspend fun updateLink(link: ILink) {
        val original = linkDao.getLinkById(link.id)
        val modified = linkMapper.map(link)
        val newTags = findNewTags(original = original.tags, modified = modified.tags)
        val deletedTags = findDeletedTags(original = original.tags, modified = modified.tags)

        linkDao.update(modified.link)
        for (tag in newTags) addTag(tag = tag, linkId = link.id)
        for (tag in deletedTags) removeTag(tag = tag, linkId = link.id)
    }

    /**
     * 링크검색 함수
     * folderId를 받아서 0(디폴트값)이면 전체링크에서 링크 검색
     * folderId가 0이 아니면 해당 folderId에 소속된 링크 검색
     */
    fun searchLinkByUrl(searchUrl: String, folderId: Long): Flow<List<ILink>> {
        val searchUrlFlow: Flow<List<LinkWithTags>> =
            if (folderId == 0L) linkDao.searchLinkUrl(searchUrl)
            else linkDao.searchLinkUrl(
                searchUrl,
                folderId)

        return searchUrlFlow
            .flowOn(Dispatchers.IO)
            .conflate()
            .map { linkMapper.map(it) }
    }

    suspend fun deleteLinks(links: List<ILink>) {
        val entities = linkMapper.map(links)
        val ids = entities.map { it.link.id }; "삭제할 링크: $ids".log()
        linkDao.deleteLinks(ids)
    }

    private suspend fun addTag(tag: TagEntity, linkId: Long) {
        linkDao.insert(tag)
        linkDao.insert(LinkTagRef(linkId = linkId, tag = tag.name))
    }

    /** 태그와 연결된 링크가 1개 뿐이면 완전 삭제하고, 여러 개이면 연결만 끊는다. */
    private suspend fun removeTag(tag: TagEntity, linkId: Long) {
        val count = linkDao.countLinksByTag(tag.name)
        if (count == 1)
            linkDao.delete(tag)
        else
            linkDao.delete(LinkTagRef(linkId = linkId, tag = tag.name))
    }

    private suspend fun getFavicon(url: Url) : Bitmap {
        val faviconUrl = url.getFaviconUrl()
        val rawResponse = linkApi.fetchUrl(faviconUrl).body()
        return bitmapMapper.map(rawResponse)
    }

    private suspend fun getImage(url: Url): Bitmap {
        val metaImg: String? = url.getMetaImg()?.get("image")
        return bitmapMapper.map(metaImg)
    }

    private fun findNewTags(original: List<TagEntity>, modified: List<TagEntity>): List<TagEntity> {
        return modified.filter { !original.contains(it) }
    }

    private fun findDeletedTags(original: List<TagEntity>, modified: List<TagEntity>): List<TagEntity> {
        return original.filter { !modified.contains(it) }
    }
}