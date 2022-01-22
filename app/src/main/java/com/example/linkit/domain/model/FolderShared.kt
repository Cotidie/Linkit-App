package com.example.linkit.domain.model

import android.graphics.Bitmap
import com.example.linkit.domain.interfaces.IFolder

class FolderShared(
    override val id: Long,
    override var name: String,
    override var image: Bitmap?,
    val snode: Long,
    val gid: Long
) : IFolder {

}