package com.example.linkit.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.linkit.domain.model.EMPTY_LONG
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.view.ContentScreen
import com.example.linkit.presentation.view.LinkList
import com.example.linkit.presentation.view.ProfileScreen
import com.example.linkit.presentation.view.SearchResultScreen
import com.example.linkit.presentation.viewmodel.ExplorerViewModel

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
            route = Screen.Explorer.route.plus("/{folderId}"),
            arguments = listOf(
                navArgument("folderId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val viewModel = hiltViewModel<ExplorerViewModel>(cxt() as ViewModelStoreOwner)
            val folderId = backStackEntry.arguments?.getLong("folderId")!!

            viewModel.clearScreen()
            viewModel.setCurrentFolder(folderId)
            Explorer(navController, viewModel, folderId)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Screen.LinkList.route){
            LinkList(navController)
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
        composable(
            route = Screen.SearchResult.route.plus("?searchUrl={searchUrl}&folderId={folderId}"),
            arguments = listOf(
                navArgument("folderId") {
                    type = NavType.LongType
                    defaultValue = EMPTY_LONG
                },
                navArgument("searchUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val folderId = backStackEntry.arguments?.getLong("folderId")
            val searchUrl = backStackEntry.arguments?.getString("searchUrl")

            SearchResultScreen(navController,folderId=folderId!!, searchUrl = searchUrl!!)
        }
    }

}