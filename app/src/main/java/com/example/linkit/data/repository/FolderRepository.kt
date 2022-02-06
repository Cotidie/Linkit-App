package com.example.linkit.data.repository

import com.example.linkit.data.repository.dto.FolderMappers
import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.domain.interfaces.IFolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/** 도메인 모델 [FolderPrivate], [FolderShared]를 담당하는 Repository */
class FolderRepository @Inject constructor(
    private val folderDao: FolderDao,
    private val folderDto: FolderMappers
){
    fun getAllFolders() : Flow<List<IFolder>> {
        return folderDao.getAllFolders()
            .flowOn(Dispatchers.IO)
            .conflate()
            .map { folderDto.map(it) }
    }

    suspend fun getFolder(id: Long) : IFolder = folderDao.getFolderById(id).run { folderDto.map(this) }
    suspend fun insert(folder: IFolder) = folderDao.insert(folderDto.map(folder))
    suspend fun update(folder: IFolder) = folderDao.update(folderDto.map(folder))
    suspend fun delete(folder: IFolder) = folderDao.delete(folderDto.map(folder))
    suspend fun deleteAll() = folderDao.deleteAll()
}