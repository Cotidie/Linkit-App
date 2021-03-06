package com.apptive.linkit.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apptive.linkit._enums.SearchType
import com.apptive.linkit.domain.model.EMPTY_LONG
import com.apptive.linkit.domain.model.log
import com.apptive.linkit.presentation.navigation.Screen
import com.apptive.linkit.presentation.view.ContentScreen
import com.apptive.linkit.presentation.view.LinkList
import com.apptive.linkit.presentation.view.ProfileScreen
import com.apptive.linkit.presentation.view.SearchResultScreen
import com.apptive.linkit.presentation.viewmodel.ExplorerViewModel
import com.apptive.linkit.presentation.viewmodel.SearchViewModel

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
            route = Screen.SearchResult.route.plus("?searchText={searchText}&folderId={folderId}&searchType={searchType}"),
            arguments = listOf(
                navArgument("folderId") {
                    type = NavType.LongType
                    defaultValue = EMPTY_LONG
                },
                navArgument("searchText") { type = NavType.StringType },
                navArgument("searchType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val viewModel = hiltViewModel<SearchViewModel>()
            val folderId = backStackEntry.arguments?.getLong("folderId")
            val searchText = backStackEntry.arguments?.getString("searchText")
            val searchType = backStackEntry.arguments?.getString("searchType")

            with(viewModel) {
                setParentFolder(folderId)
                setSearchText(searchText)
                setSearchType(searchType)
            }

            SearchResultScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }

}