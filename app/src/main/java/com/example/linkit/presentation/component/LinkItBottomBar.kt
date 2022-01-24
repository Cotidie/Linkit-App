package com.example.linkit.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.linkit.ui.theme.LinkItTheme

@Composable
fun LinkItBottomBar(
    modifier: Modifier = Modifier,
    iconSize: Dp = 30.dp
) {
    val selectedIndex by remember { mutableStateOf(1) }

    BottomNavigation(
        modifier = Modifier.height(66.dp),
        backgroundColor = Color.White,
        elevation = 2.dp
    ) {
        BottomNavigationItem(
            selected = (selectedIndex == 0),
            icon = {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Filled.Link,
                    contentDescription = null
                )
            },
            label = {Text("링크")},
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            selected = (selectedIndex == 1),
            icon = {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Outlined.Home,
                    contentDescription = null
                )
            },
            label = {Text("홈")},
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            selected = (selectedIndex == 2),
            icon = {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Filled.AddLink,
                    contentDescription = null
                )
            },
            label = {Text("추가")},
            onClick = { /*TODO*/ }
        )
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    LinkItTheme {
        LinkItBottomBar()
    }
}