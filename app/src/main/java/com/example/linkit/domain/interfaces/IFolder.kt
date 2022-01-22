package com.example.linkit.domain.interfaces

import android.graphics.Bitmap

/** 폴더의 기본 형식을 정의한다. */
interface IFolder {
    val id: Long
    var name: String
    var image: Bitmap?
}