package com.example.linkit.data.repository

import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.data.room.entity.Link
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 도메인 모델 Link를 담당하는 Repository
 */
class LinkRepository @Inject constructor(private val linkDao: LinkDao) {
    suspend fun addLink(link: Link) = linkDao.insert(link)
    suspend fun updateLink(link: Link) = linkDao.update(link)
    suspend fun deleteLink(link: Link) = linkDao.deleteLink(link)
    suspend fun deleteAllLinks() = linkDao.delete()
    fun getAllLinks() : Flow<List<Link>>
    // Flow를 IO 쓰레드에서 동작시키고, emit과 collector를 다른 코루틴에서 실행시킨다 (conflate)
    = linkDao.getLinks().flowOn(Dispatchers.IO).conflate()
}