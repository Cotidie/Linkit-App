package com.example.linkit.domain.model

import android.graphics.Bitmap
import com.example.linkit.domain.interfaces.IFolder
import java.util.*

class FolderPrivate(
    override val id: Long,
    override var name: String,
    override var image: Bitmap = EMPTY_BITMAP,
    override val created: Date = Date()
) : IFolder {
    override fun isShared(): Boolean = false
}
