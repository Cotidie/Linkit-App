package com.example.linkit.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.linkit.constant.UIConstants

@Composable
fun IconTextButtonVert(
    icon: ImageVector? = null,
    iconSize: Dp = UIConstants.ICON_SIZE_APP_BAR,
    text: String,
    behavior: () -> Unit = {}
) {
    Column(
        modifier = Modifier.clickable { behavior() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = icon,
                contentDescription = null
            )
            Text(
                text = text,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PreviewIconTextButtonVert() {
    IconTextButtonVert(
        icon = Icons.Filled.Delete,
        text = "삭제"
    )
}