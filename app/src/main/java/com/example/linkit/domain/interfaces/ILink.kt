package com.example.linkit.domain.interfaces

import android.graphics.Bitmap
import com.example.linkit.domain.model.Url

interface ILink : IFile{
    val id: Long
    override var name: String
    var memo: String
    var url: Url
    var image: Bitmap
    val tags: List<String>
}