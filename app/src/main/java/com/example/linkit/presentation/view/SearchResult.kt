package com.example.linkit.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.UIMode
import com.example.linkit.presentation.component.AppBarSearch
import com.example.linkit.presentation.component.CardLink
import com.example.linkit.presentation.model.LinkView
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.viewmodel.SearchViewModel

@Composable
fun SearchResultScreen(
    navController: NavController,
    viewModel: SearchViewModel,
) {
    val scrollState = rememberLazyListState()
    var searchText by viewModel.searchText
    var searchType by viewModel.searchType
    val links by viewModel.searchedLinks

    LaunchedEffect(searchText) {
        viewModel.searchLinks()
    }

    Scaffold(
        topBar = {
            AppBarSearch(
                modifier = Modifier.height(UIConstants.HEIGHT_APP_BAR),
                text = searchText,
                initialSearchType = searchType,
                onClearClicked = { searchText = "" },
                onTextChange = { searchText = it },
                onSearchClicked = { viewModel.searchLinks() },
                onSearchTypeChange = { searchType = it }
            )
        }
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(innerPadding)
        ) {
            SearchLinkContent(
                navController = navController,

                links = viewModel.searchedLinks.value,
                scrollState = scrollState,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchLinkContent(
    navController: NavController,
    links: List<LinkView>,
    scrollState: LazyListState = rememberLazyListState(),
) {

    LazyColumn(
        modifier = Modifier
            .padding(top=20.dp, start=25.dp, end=25.dp),
        state = scrollState
    ) {
        items(links) { link ->
            Spacer(Modifier.height(20.dp))
            CardLink(
                modifier = Modifier.height(80.dp),
                link = link,
                onIconClick = {
                    navController.navigate(
                        route = Screen.Content.route.plus("/${link.id}")
                    )
                },
                uiMode = UIMode.NORMAL
            )
        }
    }
}