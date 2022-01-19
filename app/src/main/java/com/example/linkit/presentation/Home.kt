package com.example.linkit.presentation

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkit.domain.model.User
import com.example.linkit.viewmodel.HomeViewModel

@Composable
fun Home() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val user = homeViewModel.user.collectAsState(initial = User.GUEST).value

    // Pref에 User 정보 추가
    TextButton(onClick = {
        homeViewModel.save()
        homeViewModel.flag = !homeViewModel.flag
    }) {
        Text(user.toString())
    }
}