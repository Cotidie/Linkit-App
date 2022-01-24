package com.example.linkit.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tagTable")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tagId")
    val id: Long = 0,
    @ColumnInfo(name="name")
    val name: String
)