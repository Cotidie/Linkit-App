package com.example.linkit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.linkit.data.room.dao.FolderDao
import com.example.linkit.data.room.dao.LinkDao
import com.example.linkit.data.room.entity.FolderModel
import com.example.linkit.data.room.entity.LinkModel

@Database(entities = [LinkModel::class, FolderModel::class], version = 1, exportSchema = true)
abstract class LinkItDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
    abstract fun folderDao() : FolderDao
}
