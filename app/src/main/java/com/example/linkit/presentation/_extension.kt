package com.example.linkit.presentation

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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


/** 애니메이션 Wrapper */
@Composable
fun AnimatePopup(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    val enterSpec = slideInVertically(
        animationSpec = TweenSpec(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        initialOffsetY = { it }
    )
    val exitSpec = slideOutVertically { it }

    AnimatedVisibility(
        visible = visible,
        enter = enterSpec,
        exit = exitSpec,
        content = content
    )
}