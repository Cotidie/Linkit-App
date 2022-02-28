package com.apptive.linkit.presentation.component

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
import com.apptive.linkit._constant.UIConstants
import com.apptive.linkit._enums.SearchBarState.CLOSED
import com.apptive.linkit._enums.SearchBarState.OPENED
import com.apptive.linkit._enums.SearchType
import com.apptive.linkit._enums.SortingOption
import com.apptive.linkit.presentation.navigation.Screen
import com.apptive.linkit.presentation.viewmodel.ExplorerViewModel

@Composable
fun AppBarExplorer(
    viewModel: ExplorerViewModel,
    modifier : Modifier = Modifier,
    folderName: String,
    folderId: Long,
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
                searchType = searchType,
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

    AppBarSorting(
        title = {
            FolderNameAndBackButton(
                folderName = folderName,
                onBackClick = onBackClick
            )
        },
        sortBy = sortBy,
        onSearchClick = onSearchClick,
        onSortChange = { sortBy = it }
    )
}

@Composable
private fun FolderNameAndBackButton(
    folderName: String,
    onBackClick: () -> Unit
) {
    Icon(
        modifier = Modifier
            .padding(start = 10.dp, end = 2.dp)
            .clickable { onBackClick() },
        imageVector = Icons.Filled.ArrowBackIos,
        contentDescription = null
    )
    Text(text = folderName)
}