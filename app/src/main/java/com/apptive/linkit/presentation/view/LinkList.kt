package com.apptive.linkit.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.apptive.linkit.presentation.component.AppBottomBar
import com.apptive.linkit.presentation.view.LinkList.AppBarLinkList
import com.apptive.linkit.presentation.view.LinkList.LinkCards
import com.apptive.linkit.presentation.viewmodel.LinkListViewModel


@Composable
fun LinkList(
    navController: NavController,
) {
    val viewModel = hiltViewModel<LinkListViewModel>()
    val scrollState = rememberLazyListState()
    val uiMode by viewModel.uiMode

    Scaffold(
        topBar = {
            AppBarLinkList(viewModel = viewModel)
        },
        bottomBar = {
            AppBottomBar(
                uiMode = uiMode,
                navController = navController,
            )
        }
    ) { innerPadding ->
        ScreenBackground(innerPadding) {
            ScreenContent {
                LinkCards(
                    navController = navController,
                    links = viewModel.displayLinks,
                    scrollState = scrollState,
                )
            }
        }
    }
}

@Composable
private fun ScreenBackground(
    innerPadding: PaddingValues,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.DarkGray),
        content = content
    )
}

@Composable
private fun ScreenContent(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 25.dp, end = 25.dp),
        content = content
    )
}

