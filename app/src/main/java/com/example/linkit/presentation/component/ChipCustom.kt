package com.example.linkit.presentation.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

/** TODO: modifier는 하나만 두고, 배경색, 패딩 등 해당하는 값을 직접 받기 */
@Composable
fun CustomChip(
    modifier : Modifier = Modifier,
    textModifier : Modifier = Modifier,
    text: String,
    backgroundColor: Color = Color.Transparent
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = backgroundColor
    ) {
        Text(
            modifier = textModifier,
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

