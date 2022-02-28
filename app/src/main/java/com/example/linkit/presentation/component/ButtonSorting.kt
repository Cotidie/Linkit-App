package com.example.linkit.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.SortingOption

@Composable
fun SortingButton(
    currentSorting: SortingOption,
    onItemClick: (SortingOption) -> Unit = {}
) {
    val options = SortingOption.values()

    DropDownButton(
        items = options.toList(),
        button = { expand ->
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(UIConstants.SIZE_ICON_MEDIUM)
                    .clickable { expand() },
                imageVector = Icons.Filled.Tune,
                contentDescription = null,
                tint = Color.Black
            )
        },
        item = { index, item ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    text = item.text,
                    textAlign = TextAlign.Center
                )

                if (item == currentSorting) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.1f))
                    )
                }
            }
        },
        itemPadding = PaddingValues(0.dp),
        onItemClick = { onItemClick(it) }
    )
}