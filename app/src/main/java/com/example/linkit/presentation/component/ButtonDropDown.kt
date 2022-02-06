package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> DropDownButton(
    modifier: Modifier = Modifier,
    items: List<T>,
    button: @Composable () -> Unit,
    item: @Composable (index: Int, item: T) -> Unit,
    onItemClick: (item: T) -> Unit,
    onDismissRequest: () -> Unit,
    expanded: Boolean
) {
    Box(modifier = modifier
        .wrapContentSize(Alignment.CenterEnd)
    ) {
        button()
        DropdownMenu(
            modifier = Modifier.width(140.dp),
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.size(150.dp, 35.dp),
                    onClick = { onItemClick(item) }
                ) {
                    item(index, item)
                }
            }
        }
    }
}