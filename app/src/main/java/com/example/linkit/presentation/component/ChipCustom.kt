package com.example.linkit.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** TODO: modifier는 하나만 두고, 배경색, 패딩 등 해당하는 값을 직접 받기 */
@Composable
fun CustomChip(
    text: String,
    prefix: String = "",
    borderWidth: Dp = (-1).dp,
    borderColor: Color = Color.Black,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = CircleShape,
        color = backgroundColor,
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Text(text = prefix)

        Text(
            modifier = Modifier.padding(horizontal=10.dp),
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Preview
@Composable
fun PreviewTagChip() {
    CustomChip(text = "# 테스트")
}

