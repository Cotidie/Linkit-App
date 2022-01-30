package com.example.linkit.domain.model

import android.graphics.Bitmap
import com.example.linkit.domain.interfaces.IFolder
import java.util.*

class FolderShared(
    override val id: Long,
    override var name: String,
    override var image: Bitmap?,
    override val created: Date = Date(),
    val snode: Long,
    val gid: Long,
) : IFolder {
    override fun isShared(): Boolean = true
}