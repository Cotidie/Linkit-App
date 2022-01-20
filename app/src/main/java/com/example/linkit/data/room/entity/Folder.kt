package com.example.linkit.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_table")
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="image")
    val image: String
)