package com.example.linkit.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "link_table")
data class Link(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name="memo")
    val memo: String,
    @ColumnInfo(name="url")
    val url: String,
    @ColumnInfo(name="tags")
    val tags: List<String>,
    @ColumnInfo(name="image")
    val image: String
)
