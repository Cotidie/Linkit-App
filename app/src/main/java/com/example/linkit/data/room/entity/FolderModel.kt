package com.example.linkit.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_table")
data class FolderModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="image")
    val image: String,
    // 아래 두 컬럼은 공유폴더 전용
    @ColumnInfo(name="snode")
    val snode: Long? = null,
    @ColumnInfo(name="gid")
    val gid: Long? = null
)