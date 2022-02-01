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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.linkit._enums.AnimationSpec
import com.example.linkit._enums.AnimationSpec.*
import kotlinx.coroutines.launch

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
    type: AnimationSpec = SLIDE_UP,
    beforeAnimation: () -> Unit = {},
    afterAnimation: () -> Unit = {},
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    beforeAnimation()
    AnimatedVisibility(
        visible = visible,
        enter = type.enter,
        exit = type.exit,
        content = content
    )

    afterAnimation()
}

@Composable
/** 픽셀을 Dp로 변환한다. */
fun Int.toDp()
    = with(LocalDensity.current) {
        this@toDp.toDp()
    }
