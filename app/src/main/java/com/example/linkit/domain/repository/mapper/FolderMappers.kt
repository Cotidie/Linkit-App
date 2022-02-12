package com.example.linkit.domain.repository.mapper

import com.example.linkit.data.room.dto.*
import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.domain.interfaces.IFolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderMappers @Inject constructor(
    private val entityMapper: EntityToFolder,
    private val folderMapper: FolderToEntity,
    private val entityListMapper: EntitiesToFolders,
    private val folderListMapper: FoldersToEntities
) {
    fun map(folder: IFolder) : FolderEntity = folderMapper.map(folder)
    fun map(folder: FolderEntity) : IFolder = entityMapper.map(folder)
    @JvmName("mapIFoldersToFolderEntities")
    fun map(folders: List<IFolder>) : List<FolderEntity> = folderListMapper.map(folders)
    @JvmName("mapFolderEntitiesToIFolders")
    fun map(folders: List<FolderEntity>) : List<IFolder> = entityListMapper.map(folders)
}