package com.example.linkit.data.repository

import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.data.room.entity.FolderEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/** 도메인 모델 Folder, SharedFolder를 담당하는 Repository */
class FolderRepository @Inject constructor(private val folderDao: FolderDao){
    fun getAllFolders() : Flow<List<FolderEntity>>
        = folderDao.getAllFolders().flowOn(Dispatchers.IO).conflate()
    suspend fun getFolder(id: Long) : FolderEntity = folderDao.getFolderById(id)
    suspend fun insert(folderEntity: FolderEntity) = folderDao.insert(folderEntity)
    suspend fun update(folderEntity: FolderEntity) = folderDao.update(folderEntity)
    suspend fun delete(folderEntity: FolderEntity) = folderDao.delete(folderEntity)
    suspend fun deleteAll() = folderDao.deleteAll()
}