package com.apptive.linkit.domain.model

import android.graphics.Bitmap
import com.apptive.linkit.domain.interfaces.IFolder
import java.util.*

class FolderPrivate(
    override val id: Long = 0,
    override var name: String,
    override var image: Bitmap = EMPTY_BITMAP,
    override val created: Date = Date()
) : IFolder {
    override fun isShared(): Boolean = false

    override fun toString(): String = "개인폴더: $id, $name"
    override fun equals(other: Any?): Boolean {
        if (other !is FolderPrivate) return false
        return id == other.id
    }
}
