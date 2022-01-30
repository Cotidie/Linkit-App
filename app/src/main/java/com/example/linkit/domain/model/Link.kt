package com.example.linkit.domain.model

import android.graphics.Bitmap
import com.example.linkit.domain.interfaces.ILink
import java.util.*

class Link(
    override val id: Long,
    override var name: String,
    override var memo: String,
    override var url: String,
    override var image: Bitmap,
    override val tags: List<String> = ArrayList(),
    override val created: Date = Date()
) : ILink