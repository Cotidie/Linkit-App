package com.example.linkit.domain.model

import android.graphics.Bitmap
import com.example.linkit.domain.interfaces.ILink
import java.util.*

class Link(
    override val id: Long = EMPTY_LONG,
    override var parentFolder: Long = EMPTY_LONG,
    override var name: String = "",
    override var memo: String = "",
    override var url: Url,
    override var image: Bitmap = EMPTY_BITMAP,
    override val tags: List<String> = ArrayList(),
    override val created: Date = Date()
) : ILink {
    companion object {
        val EMPTY = Link(Long.MIN_VALUE, EMPTY_LONG, "", "", Url(""), EMPTY_BITMAP)
        fun empty() = Link(Long.MIN_VALUE, EMPTY_LONG,"", "", Url(""), EMPTY_BITMAP)
    }
}