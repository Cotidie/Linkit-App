package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.East
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.linkit._constant.UIConstants

@Composable
fun AppBottomBarEdit(
    modifier : Modifier = Modifier,
    text: String,
    backgroundColor: Color = Color.Black,
    contentColor: Color = Color.White,
    height: Dp = UIConstants.HEIGHT_BOTTOM_HOVER,
    actions: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Column(
            modifier = Modifier.padding(25.dp, 10.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = text
            )
            Divider(
                modifier = Modifier.padding(vertical = 5.dp),
                color = Color.LightGray,
                thickness = (1.5).dp
            )
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                actions()
            }
        }
    }
}

@Preview
@Composable
fun PreviewAppBottomBarEdit() {
    AppBottomBarEdit(
        text = "# 취미",
        actions = {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                IconTextButtonVert(
                    icon = Icons.Filled.Delete,
                    iconSize = UIConstants.SIZE_ICON_BOTTOM_BAR,
                    text = "삭제"
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                IconTextButtonVert(
                    icon = Icons.Filled.East,
                    iconSize = UIConstants.SIZE_ICON_BOTTOM_BAR,
                    text = "이동"
                )
            }
        }
    )
}