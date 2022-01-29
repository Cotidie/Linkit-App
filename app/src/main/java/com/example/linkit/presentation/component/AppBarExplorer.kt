package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.linkit.constant.UIConstants
import com.example.linkit.enums.SearchBarState
import com.example.linkit.enums.SearchBarState.*

@Composable
fun AppBarExplorer(
    modifier : Modifier = Modifier,
    folderName: String
) {
    var searchBarState by remember { mutableStateOf(CLOSED) }
    var text by remember { mutableStateOf("") }

    when (searchBarState) {
        CLOSED -> {
            AppBarExplorerDefault(
                modifier = modifier,
                folderName = folderName
            )
        }
        OPENED -> {
            AppBarSearch(
                modifier = modifier,
                text = text,
                onClearClicked = {
                    if (text.isNotEmpty()) {
                        text = ""
                    } else {
                        searchBarState = CLOSED
                    }
                },
                onTextChange = { text = it }
            )
        }
    }
}

@Composable
fun AppBarExplorerDefault(
    modifier: Modifier = Modifier,
    folderName: String
) {
    TopAppBar(
        modifier = modifier
            .height(UIConstants.HEIGHT_APP_BAR),
        title = {
            Icon(
                modifier = Modifier
                    .padding(start = 10.dp, end = 2.dp),
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = null
            )
            Text(text = folderName)
        },
        actions = {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(UIConstants.ICON_SIZE),
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Black
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(UIConstants.ICON_SIZE),
                imageVector = Icons.Filled.FormatListBulleted,
                contentDescription = null,
                tint = Color.Black
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(UIConstants.ICON_SIZE),
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                tint = Color.Black
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 2.dp
    )
}