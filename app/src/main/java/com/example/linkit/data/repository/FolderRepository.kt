package com.example.linkit.data.repository

import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.data.room.dto.FolderListMapper
import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.domain.interfaces.IFolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/** 도메인 모델 Folder, SharedFolder를 담당하는 Repository */
class FolderRepository @Inject constructor(
    private val folderDao: FolderDao,
    private val folderMapper: FolderListMapper
){
    fun getAllFolders() : Flow<List<IFolder>>
        = folderDao.getAllFolders().flowOn(Dispatchers.IO).conflate().map { folderMapper.map(it) }

    suspend fun getFolder(id: Long) : FolderEntity = folderDao.getFolderById(id)
    suspend fun insert(folderEntity: FolderEntity) = folderDao.insert(folderEntity)
    suspend fun update(folderEntity: FolderEntity) = folderDao.update(folderEntity)
    suspend fun delete(folderEntity: FolderEntity) = folderDao.delete(folderEntity)
    suspend fun deleteAll() = folderDao.deleteAll()
}