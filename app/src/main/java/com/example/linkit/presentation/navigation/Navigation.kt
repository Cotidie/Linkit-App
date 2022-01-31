package com.example.linkit.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.view.ContentScreen
import com.example.linkit.presentation.view.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Home.route) {
            Home(navController)
        }
        composable(
            route = Screen.Explorer.route.plus("/{folderName}"),
            arguments = listOf(
                navArgument("folderName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val folderName = backStackEntry.arguments?.getString("folderName")
            Explorer(navController, folderName!!)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(
            route = Screen.Content.route.plus("/{linkId}"),
            arguments = listOf(
                navArgument("linkId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val linkId = backStackEntry.arguments?.getLong("linkId")
            ContentScreen(navController, linkId!!)
        }
    }

}