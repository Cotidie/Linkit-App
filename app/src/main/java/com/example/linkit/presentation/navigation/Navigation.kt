package com.example.linkit.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.view.ContentScreen
import com.example.linkit.presentation.view.ProfileScreen
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

        /**
         *  링크 검색 navigation
         *  인자를 folderId와 searchUrl을 받는다
         *  folderId는 선택 인수로 사용하지 않으면 0L(디폴트값)
         */

        composable(
            route = Screen.SearchResult.route.plus("?text={text}&folderId={folderId}"),
            arguments = listOf(
                navArgument("folderId") {
                    type = NavType.LongType
                    defaultValue = 0L
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