package com.example.linkit.presentation

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

/** Composable 함수 내에서 현재 Context 반환. 축약형으로 쓰기 위함 */
@Composable
fun cxt() : Context = LocalContext.current

/** Modifier 관련 익스텐션 */
@Composable
fun Modifier.longPress(behavior: () -> Unit) = pointerInput(Unit) {
    detectTapGestures( onLongPress = {behavior()} )
}