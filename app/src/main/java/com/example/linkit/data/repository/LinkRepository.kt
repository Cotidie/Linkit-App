package com.example.linkit.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.linkit.LinkItApp
import com.example.linkit.data.network.api.ILinkApi
import com.example.linkit.data.network.dto.BitmapMapper
import com.example.linkit.data.repository.dto.LinkMappers
import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.data.room.entity.LinkEntity
import com.example.linkit.data.room.entity.LinkTagRef
import com.example.linkit.data.room.entity.LinkWithTags
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.EMPTY_BITMAP
import com.example.linkit.domain.model.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 도메인 모델 Link를 담당하는 Repository
 */
@Singleton
class LinkRepository @Inject constructor(
    private val linkDao: LinkDao,
    private val linkDto: LinkMappers,
    private val linkApi: ILinkApi,
    private val bitmapMapper: BitmapMapper,
) {
    private val appContext = LinkItApp.cxt()

    /** Flow를 IO 쓰레드에서 동작시키고, emit과 collector를 다른 코루틴에서 실행시킨다 (conflate) */
    fun getAllLinks(): Flow<List<LinkWithTags>> {
        return linkDao.getLinks()
            .flowOn(Dispatchers.IO)
            .conflate()
    }

    fun getLinksInFolder(folderId: Long): Flow<List<ILink>> {
        return linkDao.getLinksInFolder(folderId)
            .flowOn(Dispatchers.IO)
            .conflate()
            .map { linkDto.map(it) }
    }

    /** 링크가 주어지면 이미지를 웹에서 불러오고, 그후 Room에 저장한다. */
    suspend fun addLink(link: ILink) {
        link.favicon = getFavicon(link.url)
        link.metaImg = getMetaImg(link)

        val linkWithTags = linkDto.map(link)
        // 링크 insert
        val linkId = linkDao.insert(linkWithTags.link)
        // 태그 및 다대다 테이블 insert
        for (tag in linkWithTags.tags) {
            val tagId = linkDao.insert(tag)
            linkDao.insert(LinkTagRef(linkId = linkId, tagId = tagId))
        }
    }

    suspend fun updateLink(linkEntity: LinkEntity) = linkDao.update(linkEntity)
    suspend fun deleteLink(linkEntity: LinkEntity) = linkDao.deleteLink(linkEntity)
    suspend fun deleteAllLinks() = linkDao.delete()

    /**
     * 링크검색 함수
     * folderId를 받아서 0(디폴트값)이면 전체링크에서 링크 검색
     * folderId가 0이 아니면 해당 folderId에 소속된 링크 검색
     */
    fun searchLinkByUrl(searchUrl: String, folderId: Long): Flow<List<ILink>> {
        val searchUrlFlow: Flow<List<LinkWithTags>> by lazy {
            if (folderId == 0L) linkDao.searchLinkUrl(searchUrl)
            else linkDao.searchLinkUrl(
                searchUrl,
                folderId)
        }

        return searchUrlFlow
            .flowOn(Dispatchers.IO)
            .conflate()
            .map { linkDto.map(it) }
    }

    private suspend fun getFavicon(url: Url): Bitmap {
        val faviconUrl = url.getFaviconUrl()
        val rawResponse = linkApi.fetchUrl(faviconUrl).body()
        return bitmapMapper.map(rawResponse)
    }

    /** 크롤링 함수 */
    private fun getMetaImg(link: ILink): Bitmap {
        val metaImg = link.url.getMetaImg()?.get("image")
        return stringToBitmap(metaImg)
    }

    private fun stringToBitmap(encodedString: String?): Bitmap {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            EMPTY_BITMAP
        }
    }

}