package com.example.linkit.data.room.dto

import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ListMapperImpl
import com.example.linkit.domain.interfaces.Mapper
import com.example.linkit.domain.model.EMPTY_BITMAP
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit.domain.model.FolderShared
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntityToFolder @Inject constructor() : Mapper<FolderEntity, IFolder> {
    override fun map(input: FolderEntity): IFolder {
        val image = input.image ?: EMPTY_BITMAP

        return when (input.snode == null) {
            true -> {
                FolderPrivate(
                    id = input.id,
                    name = input.name,
                    image = image
                )
            }
            false -> {
                FolderShared(
                    id = input.id,
                    name = input.name,
                    image = image,
                    snode = input.snode,
                    gid = input.gid!!
                )
            }
        }
    }
}

@Singleton
class FolderToEntity @Inject constructor() : Mapper<IFolder, FolderEntity> {
    override fun map(input: IFolder): FolderEntity {
        return when (input.isShared()) {
            true -> {
                val casted = input as FolderShared
                FolderEntity(
                    id = casted.id,
                    name = casted.name,
                    image = input.image,
                    snode = casted.snode,
                    gid = casted.gid
                )
            }
            false -> FolderEntity(
                id = input.id,
                name = input.name,
                image = input.image
            )
        }
    }
}

@Singleton
class EntitiesToFolders @Inject constructor(
    entityMapper : EntityToFolder
) : ListMapperImpl<FolderEntity, IFolder>(entityMapper)

@Singleton
class FoldersToEntities @Inject constructor(
    folderMapper : FolderToEntity
) : ListMapperImpl<IFolder, FolderEntity>(folderMapper)