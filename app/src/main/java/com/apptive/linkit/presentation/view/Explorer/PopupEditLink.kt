package com.apptive.linkit.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.East
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.apptive.linkit._constant.UIConstants
import com.apptive.linkit.presentation.viewmodel.ExplorerViewModel

@Composable
fun PopupEditLink(
    modifier: Modifier = Modifier,
    text: String,
    onDelete: () -> Unit
) {
    AppBottomBarEdit(
        modifier = modifier,
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
                iconSize = UIConstants.SIZE_ICON_MEDIUM_LARGE,
                text = "삭제",
                onClick = onDelete
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
                iconSize = UIConstants.SIZE_ICON_MEDIUM_LARGE,
                text = "이동"
            )
        }
    }
}