package com.example.linkit.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit.domain.model.Link
import com.example.linkit.R
import com.example.linkit._enums.UIMode
import com.example.linkit.presentation.component.AppBarExplorer
import com.example.linkit.presentation.component.AppBottomBar
import com.example.linkit.presentation.component.LinkCard

@Composable
fun Explorer(
    navController: NavController,
    folderName: String
) {
    val links = getSampleLinks()
    var uiMode by remember { mutableStateOf(UIMode.NORMAL) }

    // 편집 모드에서는 일반 모드로 돌아와야 한다.
    BackHandler {
        if (uiMode == UIMode.NORMAL)
            navController.popBackStack()
        else {
            uiMode = UIMode.NORMAL
        }
    }

    Scaffold(
        topBar = {
            AppBarExplorer(
                folderName = folderName,
                navController = navController
            )
        },
        bottomBar = {
            AppBottomBar(uiMode = uiMode)
        }
    ) { innerPadding ->
        ExplorerContent(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(innerPadding),
            links = links,
            onLongPress = { uiMode = UIMode.EDIT_LINK }
        )
        if (uiMode == UIMode.NORMAL) {
            Spacer(Modifier.height(25.dp))
        }
    }
}

@Composable
fun ExplorerContent(
    modifier: Modifier = Modifier,
    links: List<Link>,
    onLongPress: () -> Unit
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .padding(top=25.dp, start=40.dp, end=40.dp)
        ) {
            items(links) { link ->
                LinkCard(
                    modifier = Modifier.height(80.dp),
                    link = link,
                    onLongPress = onLongPress
                )
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun getSampleLinks() : List<Link> {
    val titles = listOf("네이버", "다음", "카카오", "우아한 형제들", "배달의 민족", "쿠팡")
    val url = "https://developer.android.com/jetpack/compose/layout?hl=ko"
    val tags = listOf("유명", "네이버", "검색")
    val links = ArrayList<Link>().apply {
        titles.forEachIndexed { index, s ->
            add(Link(
                index.toLong(), s, "", url, getBitmap(id = R.drawable.ic_sample_image_001)!!, tags
            ))
        }
    }

    return links
}

@Preview
@Composable
fun PreviewExplorer() {
    val navController = rememberNavController()

    Explorer(navController,"취미폴더")
}