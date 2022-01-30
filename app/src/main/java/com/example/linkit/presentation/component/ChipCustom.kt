package com.example.linkit.presentation.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomChip(
    modifier : Modifier = Modifier,
    textModifier : Modifier = Modifier,
    text: String
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
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

