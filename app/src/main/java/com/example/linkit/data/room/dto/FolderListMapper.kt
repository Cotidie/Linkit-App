package com.example.linkit.data.room.dto

import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ListMapper
import com.example.linkit.domain.interfaces.ListMapperImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderListMapper @Inject constructor(
    folderMapper : FolderMapper
) : ListMapperImpl<FolderEntity, IFolder>(folderMapper)