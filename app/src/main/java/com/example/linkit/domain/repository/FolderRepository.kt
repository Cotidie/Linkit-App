package com.example.linkit.domain.repository

import com.example.linkit.domain.repository.mapper.FolderMappers
import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.log
import com.example.linkit.domain.repository.mapper.LinkMappers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/** 도메인 모델 [FolderPrivate], [FolderShared]를 담당하는 Repository */
class FolderRepository @Inject constructor(
    private val folderDao: FolderDao,
    private val linkDao: LinkDao,
    private val folderMapper: FolderMappers,
){
    fun getAllFolders() : Flow<List<IFolder>> {
        return folderDao.getAllFolders()
            .flowOn(Dispatchers.IO)
            .conflate()
            .map { folderMapper.map(it) }
    }

    suspend fun getFolder(id: Long) : IFolder = folderDao.getFolderById(id).run { folderMapper.map(this) }
    suspend fun insert(folder: IFolder) = folderDao.insert(folderMapper.map(folder))
    suspend fun update(folder: IFolder) = folderDao.update(folderMapper.map(folder))

    /** 폴더와 폴더 내의 링크를 모두 삭제한다. */
    suspend fun delete(folder: IFolder) {
        val links = linkDao.getLinksInFolder(folderId = folder.id).first()
        for (link in links) linkDao.delete(link)
        folderDao.delete(folderMapper.map(folder))
    }
    suspend fun deleteAll() = folderDao.deleteAll()
}