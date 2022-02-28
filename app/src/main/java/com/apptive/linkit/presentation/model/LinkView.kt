package com.apptive.linkit.presentation.model

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.apptive.linkit.domain.model.EMPTY_BITMAP
import com.apptive.linkit.domain.model.EMPTY_LONG
import com.apptive.linkit.domain.model.Url
import java.util.*

/** Compose에서 활용할 Link 객체 */
data class LinkView(
    val id: Long = 0,
    var parentFolder: Long = EMPTY_LONG,
    val name: MutableState<String> = mutableStateOf(""),
    val memo: MutableState<String> = mutableStateOf(""),
    val url: MutableState<Url> = mutableStateOf(Url()),
    val favicon: MutableState<Bitmap> = mutableStateOf(EMPTY_BITMAP),
    val image: MutableState<Bitmap> = mutableStateOf(EMPTY_BITMAP),
    val tags: MutableList<String> = mutableListOf(""),
    val created: MutableState<Date> = mutableStateOf(Date())
) {
    fun containsInUrl(subString: String): Boolean {
        return url.value.contains(subString)
    }

    fun containsInTags(subString: String): Boolean {
        return tags.any { it.contains(subString) }
    }
}