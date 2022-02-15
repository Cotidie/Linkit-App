package com.example.linkit.presentation.view.Content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.UIMode
import com.example.linkit.presentation.component.CustomChip

@Composable
internal fun TagAddChip(
    onClick: () -> Unit = {},
) {
    CustomChip(
        text = "+ 태그",
        minWidth = UIConstants.WIDTH_TAG_DEFAULT,
        borderWidth = 1.dp,
        onClick = onClick
    )
}

@Composable
internal fun TagInputChip(
    onDone: (String) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    CustomChip(
        prefix = "#",
        minWidth = UIConstants.WIDTH_TAG_DEFAULT,
        write = true,
        focusRequester = focusRequester,
        onDone = onDone
    )
}

@Preview
@Composable
private fun Preview() {
    TagAddChip()
}