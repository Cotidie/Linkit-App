package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.UIMode
import com.example.linkit._enums.UIMode.*
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.ui.theme.LinkItTheme

@Composable
fun AppBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    height: Dp = UIConstants.HEIGHT_BOTTOM_BAR,
    iconSize: Dp = UIConstants.SIZE_ICON_LARGE,
    uiMode: UIMode = NORMAL,
    onLinkClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    BottomNavigation(
        modifier = Modifier.height(height),
        backgroundColor = Color.White,
        elevation = 2.dp
    ) {
        BottomNavigationItem(
            selected = false,
            icon = {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Filled.Link,
                    contentDescription = null
                )
            },
            label = {Text("링크")},
            onClick = onLinkClick,
            unselectedContentColor = Color.LightGray
        )
        BottomNavigationItem(
            selected = (uiMode != ADD_LINK),
            icon = {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Outlined.Home,
                    contentDescription = null
                )
            },
            label = {Text("홈")},
            onClick = {
                onHomeClick()
                navController.navigate(Screen.Home.route)
            },
            unselectedContentColor = Color.LightGray
        )
        BottomNavigationItem(
            selected = (uiMode == ADD_LINK),
            icon = {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Filled.AddLink,
                    contentDescription = null
                )
            },
            label = {Text("추가")},
            onClick = onAddClick,
            unselectedContentColor = Color.LightGray
        )
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    val navController = rememberNavController()

    LinkItTheme {
        AppBottomBar(
            navController = navController,
            uiMode = UIMode.NORMAL
        )
    }
}