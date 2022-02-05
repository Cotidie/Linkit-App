package com.example.linkit.domain.interfaces

import android.graphics.Bitmap
import com.example.linkit.domain.model.FolderPrivate

/** 폴더의 기본 형식을 정의한다. */
interface IFolder : IFile{
    val id: Long
    override var name: String
    var image: Bitmap

    fun isShared() : Boolean

    companion object {
        val DEFAULT = FolderPrivate(id=Long.MIN_VALUE, name="무제폴더")
    }
}