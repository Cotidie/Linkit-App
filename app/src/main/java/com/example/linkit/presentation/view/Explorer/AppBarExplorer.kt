package com.example.linkit.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.SearchBarState.CLOSED
import com.example.linkit._enums.SearchBarState.OPENED
import com.example.linkit._enums.SearchType
import com.example.linkit._enums.SortingOption
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.viewmodel.ExplorerViewModel

@Composable
fun AppBarExplorer(
    viewModel: ExplorerViewModel,
    modifier : Modifier = Modifier,
    folderName: String,
    folderId: Long, // 폴더 내에서 검색 구현을 위한 folderId 매개변수 추가
    navController: NavController
) {
    var searchBarState by remember { mutableStateOf(CLOSED) }
    var searchType by remember { mutableStateOf(SearchType.URL) }
    var text by remember { mutableStateOf("") }

    BackHandler(enabled = (searchBarState == OPENED)) {
        searchBarState = CLOSED
    }

    when (searchBarState) {
        CLOSED -> {
            AppBarExplorerDefault(
                viewModel = viewModel,
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
                onTextChange = { text = it },
                onSearchClicked = {
                    navController.navigate(
                        Screen.SearchResult.route.plus(
                            "?searchText=${text}&folderId=${folderId}&searchType=${searchType.text}"
                        )
                    )
                },
                onSearchTypeChange = { searchType = it }
            )
        }
    }
}

@Composable
private fun AppBarExplorerDefault(
    viewModel: ExplorerViewModel,
    modifier: Modifier = Modifier,
    folderName: String,
    onSearchClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var sortBy by viewModel.sortBy

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
                    .size(UIConstants.SIZE_ICON_MEDIUM)
                    .clickable { onSearchClick() },
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Black
            )
            SortingButton(
                currentSorting = sortBy,
                onItemClick = { sortBy = it }
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(UIConstants.SIZE_ICON_MEDIUM),
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                tint = Color.Black
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 2.dp
    )
}