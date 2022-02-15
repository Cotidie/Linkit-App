package com.example.linkit.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="tagTable")
data class TagEntity(
    @PrimaryKey
    @ColumnInfo(name="name")
    val name: String
)