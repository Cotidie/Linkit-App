package com.example.linkit.data.repository.dto

import com.example.linkit.data.room.dto.FolderListMapper
import com.example.linkit.data.room.dto.FolderMapper
import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.domain.interfaces.IFolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderMappers @Inject constructor(
    private val entityMapper: FolderMapper,
    private val entityListMapper: FolderListMapper
) {
    fun map(folder: IFolder) : FolderEntity = entityMapper.mapFrom(folder)
    fun map(folder: FolderEntity) : IFolder = entityMapper.mapTo(folder)
    @JvmName("mapIFoldersToFolderEntities")
    fun map(folders: List<IFolder>) : List<FolderEntity> = entityListMapper.mapFrom(folders)
    @JvmName("mapFolderEntitiesToIFolders")
    fun map(folders: List<FolderEntity>) : List<IFolder> = entityListMapper.mapTo(folders)
}