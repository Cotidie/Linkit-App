package com.apptive.linkit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apptive.linkit.data.room.dao.FolderDao
import com.apptive.linkit.data.room.dao.LinkDao
import com.apptive.linkit.data.room.entity.FolderEntity
import com.apptive.linkit.data.room.entity.LinkEntity
import com.apptive.linkit.data.room.entity.LinkTagRef
import com.apptive.linkit.data.room.entity.TagEntity

@Database(
    entities = [LinkEntity::class, FolderEntity::class, LinkTagRef::class, TagEntity::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LinkItDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
    abstract fun folderDao() : FolderDao
}
