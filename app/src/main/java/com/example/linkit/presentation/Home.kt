package com.example.linkit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkit.domain.model.User
import com.example.linkit.presentation.component.LinkItAppBar
import com.example.linkit.presentation.component.LinkItBottomBar
import com.example.linkit.ui.theme.LinkItTheme
import com.example.linkit.viewmodel.HomeViewModel

@Composable
fun Home() {
//    val homeViewModel = hiltViewModel<HomeViewModel>()
//    val user = homeViewModel.user.collectAsState(initial = User.GUEST).value

    Scaffold(
        topBar = {
            LinkItAppBar()
        },
        bottomBar = {
            LinkItBottomBar(
                iconSize = 32.dp
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    LinkItTheme {
        Home()
    }
}