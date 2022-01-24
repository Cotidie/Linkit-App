package com.example.linkit.domain.model

import android.graphics.Bitmap

class Link(
    private val id: Long,
    var title: String,
    var memo: String,
    var url: String,
    var image: Bitmap,
    val tags: List<String>
) { }