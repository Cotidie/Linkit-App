package com.example.linkit.presentation.model

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.linkit.domain.model.EMPTY_BITMAP
import com.example.linkit.domain.model.EMPTY_LONG
import com.example.linkit.domain.model.Url
import java.util.*

/** Compose에서 활용할 Link 객체 */
class LinkView(
    val id: Long = 0,
    var parentFolder: Long = EMPTY_LONG,
    var name: MutableState<String> = mutableStateOf(""),
    var memo: MutableState<String> = mutableStateOf(""),
    var url: MutableState<Url> = mutableStateOf(Url()),
    var favicon: MutableState<Bitmap> = mutableStateOf(EMPTY_BITMAP),
    var image: MutableState<Bitmap> = mutableStateOf(EMPTY_BITMAP),
    val tags: MutableList<String> = mutableListOf(""),
    var created: MutableState<Date> = mutableStateOf(Date())
)