package com.example.linkit.domain.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/** '디버그' 태그를 앞에 자동을 붙여준다. */
fun String.log() {
    Log.d("디버그", this)
}