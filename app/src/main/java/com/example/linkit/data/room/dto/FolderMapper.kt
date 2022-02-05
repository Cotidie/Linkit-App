package com.example.linkit.data.room.dto

import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.Mapper
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit.domain.model.FolderShared
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderMapper @Inject constructor() : Mapper<FolderEntity, IFolder> {
    override fun mapTo(input: FolderEntity): IFolder {
        return when (input.snode == null) {
            true -> {
                FolderPrivate(
                    id = input.id,
                    name = input.name,
                )
            }
            false -> {
                FolderShared(
                    id = input.id,
                    name = input.name,
                    snode = input.snode,
                    gid = input.gid!!
                )
            }
        }
    }

    override fun mapFrom(input: IFolder) : FolderEntity {
        return when (input.isShared()) {
            true -> {
                val casted = input as FolderShared
                FolderEntity(
                    id = casted.id,
                    name = casted.name,
                    image = "임시",
                    snode = casted.snode,
                    gid = casted.gid
                )
            }
            false -> FolderEntity(
                id = input.id,
                name = input.name,
                image = "임시"
            )
        }
    }
}