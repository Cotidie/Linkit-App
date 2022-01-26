package com.example.linkit.domain.model

import android.graphics.Bitmap
import com.example.linkit.domain.interfaces.IFolder

class FolderPrivate(
    override val id: Long,
    override var name: String,
    override var image: Bitmap?
) : IFolder {
    override fun isShared(): Boolean = false
}
