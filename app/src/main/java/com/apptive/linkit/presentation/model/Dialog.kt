package com.apptive.linkit.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class Dialog {
    var _show: MutableState<Boolean> = mutableStateOf(false)
    val show: Boolean
        get() = _show.value

    fun show() { _show.value = true }
    fun dismiss() { _show.value = false }
}