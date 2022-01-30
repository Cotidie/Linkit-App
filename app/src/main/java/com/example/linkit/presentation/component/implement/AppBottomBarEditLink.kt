package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.East
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.linkit._constant.UIConstants

@Composable
fun AppBottomBarEditLink(
    text: String
) {
    AppBottomBarEdit(
        text = text
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconTextButtonVert(
                icon = Icons.Filled.Delete,
                iconSize = UIConstants.ICON_SIZE_BOTTOM_HOVER,
                text = "삭제"
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconTextButtonVert(
                icon = Icons.Filled.East,
                iconSize = UIConstants.ICON_SIZE_BOTTOM_HOVER,
                text = "이동"
            )
        }
    }
}