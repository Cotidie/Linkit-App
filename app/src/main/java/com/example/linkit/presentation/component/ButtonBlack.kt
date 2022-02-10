package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
/** LinkIt 전용 검정색 버튼 */
fun ButtonBlack(
    text: String,
    minWidth: Dp? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
) {
    var modifier: Modifier = Modifier
    if (minWidth != null) modifier = modifier.defaultMinSize(minWidth = minWidth)

    IconTextButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        contentPadding = contentPadding
    )
}