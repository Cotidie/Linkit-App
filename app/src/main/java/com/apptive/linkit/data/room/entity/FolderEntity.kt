package com.apptive.linkit.data.room.entity

import android.graphics.Bitmap
import androidx.room.*

@Entity(tableName = "folderTable")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="folderId")
    val id: Long = 0,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="image")
    val image: Bitmap? = null,
    // 아래 두 컬럼은 공유폴더 전용
    @ColumnInfo(name="snode")
    val snode: Long? = null,
    @ColumnInfo(name="gid")
    val gid: Long? = null
)

data class FolderWithLinks(
    @Embedded val folder: FolderEntity,

    @Relation(
        parentColumn = "folderId",
        entityColumn = "parentFolderId"
    )
    val links: List<LinkEntity>
)