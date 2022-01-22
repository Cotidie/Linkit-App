package com.example.linkit.data.repository

import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.data.room.entity.LinkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 도메인 모델 Link를 담당하는 Repository
 */
class LinkRepository @Inject constructor(private val linkDao: LinkDao) {
    fun getAllLinks() : Flow<List<LinkModel>>
    // Flow를 IO 쓰레드에서 동작시키고, emit과 collector를 다른 코루틴에서 실행시킨다 (conflate)
            = linkDao.getLinks().flowOn(Dispatchers.IO).conflate()
    suspend fun addLink(linkModel: LinkModel) = linkDao.insert(linkModel)
    suspend fun updateLink(linkModel: LinkModel) = linkDao.update(linkModel)
    suspend fun deleteLink(linkModel: LinkModel) = linkDao.deleteLink(linkModel)
    suspend fun deleteAllLinks() = linkDao.delete()
}