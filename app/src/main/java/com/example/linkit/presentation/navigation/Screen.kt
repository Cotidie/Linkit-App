package com.example.linkit.presentation.navigation

/** 내비게이션으로 이동 가능한 화면들 */
sealed class Screen(val route: String) {
    object Splash : Screen("screen_splash")
    object Login: Screen("screen_login")
    object Home : Screen("screen_home")
}
