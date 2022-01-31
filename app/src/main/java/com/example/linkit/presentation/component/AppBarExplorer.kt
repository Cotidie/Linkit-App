package com.example.linkit.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.SearchBarState.*

@Composable
fun AppBarExplorer(
    modifier : Modifier = Modifier,
    folderName: String,
    navController: NavController
) {
    var searchBarState by remember { mutableStateOf(CLOSED) }
    var text by remember { mutableStateOf("") }

    when (searchBarState) {
        CLOSED -> {
            AppBarExplorerDefault(
                modifier = modifier,
                folderName = folderName,
                onSearchClick = { searchBarState = OPENED },
                onBackClick = { navController.popBackStack() }
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
    folderName: String,
    onSearchClick: () -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier
            .height(UIConstants.HEIGHT_APP_BAR),
        title = {
            Icon(
                modifier = Modifier
                    .padding(start = 10.dp, end = 2.dp)
                    .clickable { onBackClick() },
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = null
            )
            Text(text = folderName)
        },
        actions = {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(UIConstants.SIZE_ICON_APP_BAR)
                    .clickable { onSearchClick() },
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Black
            )
            SortingButton()
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(UIConstants.SIZE_ICON_APP_BAR),
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                tint = Color.Black
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 2.dp
    )
}

@Composable
fun SortingButton() {
    val options = listOf(
        "최근 저장 순", "오래된 순", "내 설정 순"
    )
    var selected by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }

    DropDownButton(
        expanded = expanded,
        items = options,
        button = {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(UIConstants.SIZE_ICON_APP_BAR)
                    .clickable { expanded = !expanded },
                imageVector = Icons.Filled.Tune,
                contentDescription = null,
                tint = Color.Black
            )
        },
        item = { index, item ->
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                text = item,
                textAlign = TextAlign.Center
            )
        },
        onItemClick = { item ->
            selected = item
            expanded = false
        },
        onDismissRequest = { expanded = false }
    )
}