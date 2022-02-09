package com.example.linkit.domain.interfaces

import android.graphics.Bitmap
import com.example.linkit.domain.model.Url

interface ILink : IFile{
    val id: Long
    var parentFolder: Long // 링크는 어느 폴더에 속해야 한다.
    override var name: String
    var memo: String
    var url: Url
    var favicon: Bitmap // image -> favicon 으로 변경
    var metaImg : Bitmap
    val tags: List<String>
}