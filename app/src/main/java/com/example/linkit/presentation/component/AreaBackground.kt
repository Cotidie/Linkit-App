package com.example.linkit.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.linkit._constant.ColorConstants

@Composable
/** 배경색, 전체 패딩을 설정한다. */
fun BackgroundArea(
    innerPadding: PaddingValues,
    color: Color,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(color),
        content = content
    )
}