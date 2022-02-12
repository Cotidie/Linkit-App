package com.example.linkit.data.room.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "linkTable")
data class LinkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "linkId")
    val id: Long = 0,
    @ColumnInfo(name = "parentFolderId")
    val folderId: Long,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name="memo")
    val memo: String = "",
    @ColumnInfo(name="url")
    val url: String,
    @ColumnInfo(name="favicon")
    val favicon: Bitmap,
    @ColumnInfo(name="image")
    val image: Bitmap,
    @ColumnInfo(name="snode")
    val snode: Long? = null,
    @ColumnInfo(name="gid")
    val gid: Long? = null
)
