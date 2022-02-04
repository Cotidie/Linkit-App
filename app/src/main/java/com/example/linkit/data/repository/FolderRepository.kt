package com.example.linkit.data.repository

import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.data.room.dto.FolderListMapper
import com.example.linkit.data.room.dto.FolderMapper
import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.domain.interfaces.IFolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/** 도메인 모델 Folder, SharedFolder를 담당하는 Repository */
class FolderRepository @Inject constructor(
    private val folderDao: FolderDao,
    private val folderMapper: FolderMapper,
    private val folderListMapper: FolderListMapper
){
    fun getAllFolders() : Flow<List<IFolder>>
        = folderDao.getAllFolders().flowOn(Dispatchers.IO).conflate().map { folderListMapper.mapTo(it) }

    suspend fun getFolder(id: Long) : FolderEntity = folderDao.getFolderById(id)
    suspend fun insert(folder: IFolder) {
        folderDao.insert(folderMapper.mapFrom(folder))
    }
    suspend fun update(folder: IFolder) = folderDao.update(folderMapper.mapFrom(folder))
    suspend fun delete(folder: IFolder) = folderDao.delete(folderMapper.mapFrom(folder))
    suspend fun deleteAll() = folderDao.deleteAll()
}