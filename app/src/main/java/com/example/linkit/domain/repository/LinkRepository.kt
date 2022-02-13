package com.example.linkit.domain.repository

import android.graphics.Bitmap
import com.example.linkit.LinkItApp
import com.example.linkit.data.network.api.ILinkApi
import com.example.linkit.domain.repository.mapper.LinkMappers
import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.data.room.entity.LinkTagRef
import com.example.linkit.data.room.entity.LinkWithTags
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
    private val appContext = LinkItApp.cxt()

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

    /** 링크가 주어지면 이미지를 웹에서 불러오고, 그후 Room에 저장한다. */
    suspend fun addLink(link: ILink) {
        link.favicon = getFavicon(link.url)
        link.image = getImage(link.url)
        val linkWithTags = linkMapper.map(link)
        // 링크 insert
        val linkId = linkDao.insert(linkWithTags.link)
        // 태그 및 다대다 테이블 insert
        for (tag in linkWithTags.tags) {
            val tagId = linkDao.insert(tag)
            linkDao.insert(LinkTagRef(linkId = linkId, tagId = tagId))
        }
    }
    suspend fun deleteLinks(links: List<ILink>) {
        val entities = linkMapper.map(links)
        val ids = entities.map { it.link.id }; "삭제할 링크: $ids".log()
        linkDao.deleteLinks(ids)
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
}