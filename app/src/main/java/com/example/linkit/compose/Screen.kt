package com.example.linkit.compose

sealed class Screen(val route: String) {
    object Splash : Screen("screen_splash")
    object Home : Screen("screen_home")
}
