package com.example.linkit.presentation.model

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.linkit.domain.model.EMPTY_BITMAP
import com.example.linkit.domain.model.EMPTY_LONG

/** Compose에서 활용할 Folder 객체 */
data class FolderView(
    val id: Long = EMPTY_LONG,
    val name: MutableState<String> = mutableStateOf(""),
    val image: MutableState<Bitmap> = mutableStateOf(EMPTY_BITMAP),
    val gid: MutableState<Long?> = mutableStateOf(null),
    val snode: MutableState<Long?> = mutableStateOf(null)
) {
    fun isShared(): Boolean = (gid.value != null)
}