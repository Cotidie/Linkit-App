package com.example.linkit.presentation.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.SearchBarState.*
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.ui.theme.LinkItTheme

@Composable
fun AppBarHome(navController: NavController) {
    var searchBarState by remember { mutableStateOf(CLOSED) }
    var text by remember { mutableStateOf("") }

    BackHandler(enabled = (searchBarState == OPENED)) {
        searchBarState = CLOSED
    }

    when (searchBarState) {
        CLOSED -> {
            AppBarHomeDefault(
                onSearchClicked = { searchBarState = OPENED },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }
        OPENED -> {

            AppBarSearch(
                text = text,
                onClearClicked = {
                    if (text.isNotEmpty()) {
                        text = ""
                    } else {
                        searchBarState = CLOSED
                    }
                },
                onTextChange = { text = it },
                onSearchClicked = {
                    // AppBarHome에서는 클릭시 text 인자만 전달
                    navController.navigate(Screen.SearchResult.route.plus("?searchUrl=${text}")
                    )
                }
            )
        }
    }
}


/**
 *  Scaffold의 TopBar 영역에 삽입할 컴포넌트
 *   - 메인화면: 돋보기와 프로필 아이콘
 *   - 탐색화면: 돋보기와 정렬 아이콘
 */
@Composable
fun AppBarHomeDefault(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    onProfileClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier
            .height(UIConstants.HEIGHT_APP_BAR),
        title = {
            Icon(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(UIConstants.SIZE_ICON_MEDIUM)
                    .clickable { onSearchClicked() },
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Black
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(UIConstants.SIZE_ICON_MEDIUM)
                    .clickable { onProfileClick() },
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                tint = Color.Black
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 2.dp
    )
}

@Preview
@Composable
fun PreviewAppBar() {
    val navController = rememberNavController()

    LinkItTheme {
        AppBarHomeDefault(
            onSearchClicked = { },
            onProfileClick = { }
        )
    }
}