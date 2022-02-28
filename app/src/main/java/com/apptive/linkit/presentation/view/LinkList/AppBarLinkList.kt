package com.apptive.linkit.presentation.view.LinkList

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import com.apptive.linkit._enums.SearchBarState
import com.apptive.linkit._enums.SearchBarState.*
import com.apptive.linkit._enums.SortingOption
import com.apptive.linkit.presentation.component.AppBarSearch
import com.apptive.linkit.presentation.component.AppBarSorting
import com.apptive.linkit.presentation.viewmodel.LinkListViewModel

@Composable
fun AppBarLinkList(
    viewModel: LinkListViewModel
) {
    var searchBarState by remember { mutableStateOf(CLOSED) }
    var searchText by viewModel.searchText
    var searchBy by viewModel.searchBy
    var sortBy by viewModel.sortBy

    BackHandler(enabled = (searchBarState == OPENED)) {
        searchBarState = CLOSED
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
                searchType = searchBy,
                onClearClicked = {
                    if (searchText.isNotEmpty()) searchText = ""
                    else                         searchBarState = CLOSED
                },
                onTextChange = { searchText = it },
                onSearchTypeChange = { searchBy = it }
            )
        }
    }
}