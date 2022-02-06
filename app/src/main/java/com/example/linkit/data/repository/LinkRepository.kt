package com.example.linkit.data.repository

import android.graphics.Bitmap
import com.example.linkit.LinkItApp
import com.example.linkit.data.network.api.ILinkApi
import com.example.linkit.data.network.dto.BitmapMapper
import com.example.linkit.data.repository.dto.LinkMappers
import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.data.room.entity.LinkEntity
import com.example.linkit.data.room.entity.LinkWithTags
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 도메인 모델 Link를 담당하는 Repository
 */
class LinkRepository @Inject constructor(
    private val linkDao: LinkDao,
    private val linkDto: LinkMappers,
    private val linkApi: ILinkApi,
    private val bitmapMapper: BitmapMapper,
) {
    private val appContext = LinkItApp.cxt()

    /** Flow를 IO 쓰레드에서 동작시키고, emit과 collector를 다른 코루틴에서 실행시킨다 (conflate) */
    fun getAllLinks() : Flow<List<LinkWithTags>> {
        return linkDao.getLinks()
            .flowOn(Dispatchers.IO)
            .conflate()
    }

    fun getLinksInFolder(folderId: Long) {

    }

    /** 링크가 주어지면 이미지를 웹에서 불러오고, 그후 Room에 저장한다. */
    suspend fun addLink(link: ILink) {
        link.image = getFavicon(link.url)
        linkDao.insert(linkDto.map(link))
    }
    suspend fun updateLink(linkEntity: LinkEntity) = linkDao.update(linkEntity)
    suspend fun deleteLink(linkEntity: LinkEntity) = linkDao.deleteLink(linkEntity)
    suspend fun deleteAllLinks() = linkDao.delete()

    private suspend fun getFavicon(url: Url) : Bitmap {
        val faviconUrl = url.getFaviconUrl()
        val rawResponse = linkApi.fetchUrl(faviconUrl).body()
        return bitmapMapper.map(rawResponse)
    }

}