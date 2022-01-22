package com.example.linkit.data.repository

import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.data.room.entity.FolderModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/** 도메인 모델 Folder, SharedFolder를 담당하는 Repository */
class FolderRepository @Inject constructor(private val folderDao: FolderDao){
    fun getAllFolders() : Flow<List<FolderModel>>
        = folderDao.getAllFolders().flowOn(Dispatchers.IO).conflate()
    suspend fun getFolder(id: Long) : FolderModel = folderDao.getFolderById(id)
    suspend fun insert(folderModel: FolderModel) = folderDao.insert(folderModel)
    suspend fun update(folderModel: FolderModel) = folderDao.update(folderModel)
    suspend fun delete(folderModel: FolderModel) = folderDao.delete(folderModel)
    suspend fun deleteAll() = folderDao.deleteAll()
}