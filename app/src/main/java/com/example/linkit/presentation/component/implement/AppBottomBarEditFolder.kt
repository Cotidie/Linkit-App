package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.linkit._constant.UIConstants

@Composable
fun AppBottomBarEditFolder(
    text: String,
    onDeleteClick: () -> Unit = {},
    onRenameClick: () -> Unit = {},
    onReimageClick: () -> Unit = {}
) {
    AppBottomBarEdit(
        text = text,
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconTextButtonVert(
                icon = Icons.Filled.Delete,
                iconSize = UIConstants.SIZE_ICON_MEDIUM_LARGE,
                text = "삭제",
                onClick = onDeleteClick
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconTextButtonVert(
                icon = Icons.Filled.ModeEdit,
                iconSize = UIConstants.SIZE_ICON_MEDIUM_LARGE,
                text = "이름변경",
                onClick = onRenameClick
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconTextButtonVert(
                icon = Icons.Filled.Wallpaper,
                iconSize = UIConstants.SIZE_ICON_MEDIUM_LARGE,
                text = "사진변경",
                onClick = onReimageClick
            )
        }
    }
}