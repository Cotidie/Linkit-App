package com.apptive.linkit._enums

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

enum class AnimationSpec(
    val enter: EnterTransition,
    val exit: ExitTransition
) {
    SLIDE_UP(
        enter = slideInVertically(
            animationSpec = TweenSpec(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically { it }
    ),
    SLIDE_DOWN(
        enter = slideInVertically(
            animationSpec = TweenSpec(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            initialOffsetY = { -it }
        ),
        exit = slideOutVertically { -it },
    )
}