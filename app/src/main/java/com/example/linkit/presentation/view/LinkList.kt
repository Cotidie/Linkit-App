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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.presentation.component.AppBottomBar
import com.example.linkit.presentation.component.CardLink
import com.example.linkit.presentation.model.LinkView
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.viewmodel.LinkListViewModel


@Composable
fun LinkList(
    navController: NavController,
) {
    val viewModel = hiltViewModel<LinkListViewModel>()
    val scrollState = rememberLazyListState()
    val uiMode by viewModel.uiMode

    LaunchedEffect(Unit) {
        viewModel.collectLinks()
    }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                uiMode = uiMode,
                navController = navController,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(innerPadding)
        ) {
            LinkListContent(
                navController = navController,
                links = viewModel.links.value,
                scrollState = scrollState,
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LinkListContent(
    navController: NavController,
    links: List<LinkView>,
    scrollState: LazyListState = rememberLazyListState(),
) {

    LazyColumn(
        modifier = Modifier
            .padding(top = 20.dp, start = 25.dp, end = 25.dp),
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

