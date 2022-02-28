package com.apptive.linkit.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.apptive.linkit.presentation.model.Size
import com.apptive.linkit.presentation.size

@Composable
fun <T> DropDownButton(
    modifier: Modifier = Modifier,
    items: List<T>,
    button: @Composable (expand: () -> Unit) -> Unit,
    item: @Composable (index: Int, item: T) -> Unit,
    itemSize: Size = Size(140.dp, 35.dp),
    itemPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    onItemClick: (item: T) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .wrapContentSize(Alignment.CenterEnd)
    ) {
        button(expand = { expanded = true })
        DropdownMenu(
            modifier = Modifier.width(itemSize.width),
            offset = DpOffset(-5.dp, 5.dp),
            expanded = expanded,
            onDismissRequest = {
                onDismissRequest()
                expanded = false
            }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.size(itemSize),
                    contentPadding = itemPadding,
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