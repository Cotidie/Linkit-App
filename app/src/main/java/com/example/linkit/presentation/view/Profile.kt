package com.example.linkit.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileScreen(navController: NavController) {

}

@Preview
@Composable
fun PreviewProfile() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}