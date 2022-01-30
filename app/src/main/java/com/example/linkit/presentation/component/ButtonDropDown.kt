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

///** State 값을 분리하기 위한 Wrapper */
//@Composable
//fun <T> DropDownButton(
//    modifier: Modifier = Modifier,
//    items: List<T>,
//    button: @Composable (selected: T, showHide: () -> Unit) -> Unit,
//    item: @Composable (index: Int, item: T) -> Unit
//) {
//    var selected by remember { mutableStateOf(items[0]) }
//    var expanded by remember { mutableStateOf(false) }
//
//    DropDownButton(
//        modifier = modifier,
//        items = items,
//        button = { button(selected) {
//            expanded = !expanded
//        } },
//        item = item,
//        onItemClick = { clickedItem ->
//            selected = clickedItem
//            expanded = false
//        },
//        onDismissRequest = { expanded = false },
//        expanded = expanded
//    )
//}