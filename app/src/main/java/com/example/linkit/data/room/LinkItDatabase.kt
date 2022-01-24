package com.example.linkit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.data.room.entity.LinkEntity
import com.example.linkit.data.room.entity.LinkTagRef
import com.example.linkit.data.room.entity.TagEntity

@Database(
    entities = [LinkEntity::class, FolderEntity::class, LinkTagRef::class, TagEntity::class],
    version = 1,
    exportSchema = false)
abstract class LinkItDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
    abstract fun folderDao() : FolderDao
}
