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
    override fun map(input: FolderEntity): IFolder {
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
}