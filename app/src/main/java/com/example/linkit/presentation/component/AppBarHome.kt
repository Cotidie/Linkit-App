package com.example.linkit.presentation.component

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
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.SearchBarState.*
import com.example.linkit.ui.theme.LinkItTheme

@Composable
fun AppBarHome() {
    var searchBarState by remember { mutableStateOf(CLOSED) }
    var text by remember { mutableStateOf("") }

    when (searchBarState) {
        CLOSED -> {
            AppBarHomeDefault(
                onSearchClicked = { searchBarState = OPENED }
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
                onTextChange = { text = it }
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
    onSearchClicked: () -> Unit
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
    LinkItTheme {
        AppBarHomeDefault(
            onSearchClicked = { }
        )
    }
}