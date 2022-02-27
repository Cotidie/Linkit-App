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
    button: @Composable (expand: () -> Unit) -> Unit,
    item: @Composable (index: Int, item: T) -> Unit,
    onItemClick: (item: T) -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .wrapContentSize(Alignment.CenterEnd)
    ) {
        button(expand = { expanded = true })
        DropdownMenu(
            modifier = Modifier.width(140.dp),
            expanded = expanded,
            onDismissRequest = {
                onDismissRequest()
                expanded = false
            }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.size(150.dp, 35.dp),
                    onClick = {
                        onItemClick(item)
                        expanded = false
                    }
                ) {
                    item(index, item)
                }
            }
        }
    }
}