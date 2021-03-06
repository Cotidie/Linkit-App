package com.apptive.linkit.domain.model

import android.graphics.Bitmap
import com.apptive.linkit.domain.interfaces.ILink
import java.util.*

class Link(
    override val id: Long = 0,
    override var parentFolder: Long = EMPTY_LONG,
    override var name: String = "",
    override var memo: String = "",
    override var url: Url = Url(""),
    override var favicon: Bitmap = EMPTY_BITMAP,
    override var image: Bitmap = EMPTY_BITMAP,
    override val tags: ArrayList<String> = ArrayList(),
    override val created: Date = Date()
) : ILink {

    override fun toString(): String {
        return "Link(id: $id, url: ${url.getFullUrlString()})"
    }

    companion object {
        val EMPTY = Link()
    }
}