package com.example.linkit.domain.interfaces

import android.graphics.Bitmap

interface ILink : IFile{
    val id: Long
    override var name: String
    var memo: String
    var url: String
    var image: Bitmap
    val tags: List<String>
}