package com.example.linkit.presentation.model

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.linkit.domain.model.EMPTY_BITMAP
import com.example.linkit.domain.model.EMPTY_LONG
import com.example.linkit.domain.model.Url
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
)