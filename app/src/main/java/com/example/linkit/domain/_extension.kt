package com.example.linkit.domain.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/** '디버그' 태그를 앞에 자동을 붙여준다. */
fun String.log() {
    Log.d("디버그", this)
}

/** Composable 함수 내에서 현재 Context 반환. 축약형으로 쓰기 위함 */
@Composable
fun cxt() : Context = LocalContext.current