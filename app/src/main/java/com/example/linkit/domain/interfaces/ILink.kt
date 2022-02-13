package com.example.linkit.domain.interfaces

import android.graphics.Bitmap
import com.example.linkit.domain.model.EMPTY_LONG
import com.example.linkit.domain.model.Link
import com.example.linkit.domain.model.Url

interface ILink : IFile{
    val id: Long
    var parentFolder: Long // 링크는 어느 폴더에 속해야 한다.
    override var name: String
    var memo: String
    var url: Url
    var favicon: Bitmap
    var image: Bitmap
    val tags: ArrayList<String>

    companion object {
        val EMPTY: ILink = Link(id = EMPTY_LONG, url = Url())
    }
}