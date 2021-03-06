package com.apptive.linkit.domain.repository

import android.graphics.Bitmap
import com.apptive.linkit.data.network.api.ILinkApi
import com.apptive.linkit.domain.mapper.LinkMappers
import com.apptive.linkit.data.room.dao.LinkDao
import com.apptive.linkit.data.room.entity.LinkTagRef
import com.apptive.linkit.data.room.entity.LinkWithTags
import com.apptive.linkit.data.room.entity.TagEntity
import com.apptive.linkit.domain.interfaces.ILink
import com.apptive.linkit.domain.model.EMPTY_LONG
import com.apptive.linkit.domain.model.Url
import com.apptive.linkit.domain.model.log
import com.apptive.linkit.domain.mapper.BitmapMappers
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
    fun getAllLinks(): Flow<List<ILink>> {
        return linkDao.getLinks()
            .flowOn(Dispatchers.IO)
            .conflate()
            .map { linkMapper.map(it) }
    }

    fun getLinksInFolder(folderId: Long): Flow<List<ILink>> {
        return linkDao.getLinksInFolder(folderId)
            .flowOn(Dispatchers.IO)
            .conflate()
            .map { linkMapper.map(it) }
    }

    suspend fun getLink(linkId: Long): ILink {
        val entity = linkDao.getLinkById(linkId)
        return linkMapper.map(entity)
    }

    suspend fun getLinksByTag(tag: String, folderId: Long = EMPTY_LONG): List<ILink> {
        val searchedEntities: List<LinkWithTags>

        if (folderId == EMPTY_LONG)
            searchedEntities = linkDao.getLinksByTag(tag)
        else
            searchedEntities = linkDao.getLinksByTagInFolder(tag, folderId)

        return searchedEntities.map { linkMapper.map(it) }
    }

    suspend fun getLinksByUrl(url: String, folderId: Long = EMPTY_LONG): List<ILink> {
        val searchedEntities: List<LinkWithTags>

        if (folderId == EMPTY_LONG)
            searchedEntities = linkDao.getLinksByUrl(url)
        else
            searchedEntities = linkDao.getLinksByUrlInFolder(url, folderId)

        return searchedEntities.map { linkMapper.map(it) }
    }

    suspend fun addLink(link: ILink) {
        link.favicon = getFavicon(link.url)
        link.image = getImage(link.url)
        val entity = linkMapper.map(link)
        val linkId = linkDao.insert(entity.link)

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

    suspend fun deleteLinks(links: List<ILink>) {
        val entities = linkMapper.map(links)
        val ids = entities.map { it.link.id }; "삭제할 링크: $ids".log()
        linkDao.delete(ids)
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

    private suspend fun getFavicon(url: Url): Bitmap {
        val faviconUrl = url.getFaviconUrl()
        val rawResponse = linkApi.fetchUrl(faviconUrl).body()
        return bitmapMapper.map(rawResponse)
    }

    private fun getImage(url: Url): Bitmap {
        val metaImg: String? = url.getMetaImg()?.get("image")
        return bitmapMapper.map(metaImg)
    }

    private fun findNewTags(original: List<TagEntity>, modified: List<TagEntity>): List<TagEntity> {
        return modified.filter { !original.contains(it) }
    }

    private fun findDeletedTags(
        original: List<TagEntity>,
        modified: List<TagEntity>,
    ): List<TagEntity> {
        return original.filter { !modified.contains(it) }
    }
}