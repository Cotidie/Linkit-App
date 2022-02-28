package com.example.linkit.presentation.view.LinkList

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import com.example.linkit._enums.SearchBarState
import com.example.linkit._enums.SearchBarState.*
import com.example.linkit._enums.SortingOption
import com.example.linkit.presentation.component.AppBarSearch
import com.example.linkit.presentation.component.AppBarSorting
import com.example.linkit.presentation.viewmodel.LinkListViewModel

@Composable
fun AppBarLinkList(
    viewModel: LinkListViewModel
) {
    var searchBarState by remember { mutableStateOf(CLOSED) }
    var searchText by viewModel.searchText
    var sortBy by viewModel.sortBy

    BackHandler(enabled = (searchBarState == OPENED)) {
        searchBarState = CLOSED
    }

    LaunchedEffect(key1 = searchText) {
        viewModel.searchLinks()
    }

    when (searchBarState) {
        CLOSED -> {
            AppBarSorting(
                sortBy = sortBy,
                onSearchClick = { searchBarState = OPENED },
                onSortChange = { sortBy = it }
            )
        }
        OPENED -> {
            AppBarSearch(
                text = searchText,
                onClearClicked = {
                    if (searchText.isNotEmpty()) searchText = ""
                    else                         searchBarState = CLOSED
                },
                onTextChange = { searchText = it },
                onSearchClicked = {
                    viewModel.searchLinks()
                }
            )
        }
    }
}