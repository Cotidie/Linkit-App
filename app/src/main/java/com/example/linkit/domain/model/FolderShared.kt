package com.example.linkit.domain.model

import android.graphics.Bitmap
import com.example.linkit.domain.interfaces.IFolder
import java.util.*

class FolderShared(
    override val id: Long = 0,
    override var name: String,
    override var image: Bitmap = EMPTY_BITMAP,
    override val created: Date = Date(),
    val snode: Long,
    val gid: Long,
) : IFolder {
    override fun isShared(): Boolean = true

    override fun toString(): String = "공유폴더: $id, $name"
    override fun equals(other: Any?): Boolean {
        if (other !is FolderShared) return false
        return id == other.id
    }
}