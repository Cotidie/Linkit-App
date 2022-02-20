package com.example.linkit.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.Link
import com.example.linkit.domain.model.Url
import com.example.linkit.presentation.component.AppBarSearch
import com.example.linkit.presentation.component.CardLink
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.viewmodel.SearchViewModel

@Composable
fun SearchResultScreen(
    navController: NavController,
    folderId:Long,
    searchUrl: String
) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val scrollState = rememberLazyListState()
    var searchText by remember { mutableStateOf(searchUrl) }

    LaunchedEffect(searchText) {
        viewModel.collectLinks(searchText, folderId)
    }

    Scaffold(
        topBar = {
            AppBarSearch(
                modifier = Modifier.height(UIConstants.HEIGHT_APP_BAR),
                text = searchText,
                onClearClicked = { searchText = "" },
                onTextChange = { searchText = it }
            )
        }
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(innerPadding)
        ) {
            SearchLinkContent(
                navController = navController,

                links = viewModel.links.value.toList(),
                scrollState = scrollState,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchLinkContent(
    navController: NavController,
    links: List<ILink>,
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

@Preview
@Composable
private fun SearchResultPreview() {
    val navController = rememberNavController()
    val links = listOf(
        Link(url = Url("www.naver.com")),
        Link(url = Url("www.nver.com")),
        Link(url = Url("www.navr.com")),
        Link(url = Url("www.naer.com"))
    )
    SearchResultScreen(navController, folderId = 0, searchUrl = "naver")
}