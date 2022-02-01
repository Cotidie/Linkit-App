package com.example.linkit.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit.domain.model.Link
import com.example.linkit.R
import com.example.linkit._enums.AnimationSpec
import com.example.linkit._enums.UIMode
import com.example.linkit._enums.UIMode.*
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.component.*
import com.example.linkit.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun Explorer(
    navController: NavController,
    folderName: String
) {
    val links = getSampleLinks()
    var uiMode by remember { mutableStateOf(NORMAL) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    // 편집 모드에서는 일반 모드로 돌아와야 한다.
    BackHandler {
        if (uiMode == NORMAL)
            navController.popBackStack()
        else {
            uiMode = NORMAL
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
            AppBottomBar(
                onAddClick = {
                    uiMode = ADD_LINK
                    scope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }
            )
        }
    ) { innerPadding ->
        ExplorerContent(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(innerPadding),
            links = links,
            scrollState = scrollState,
            onLongPress = { uiMode = EDIT_LINK },
            uiMode = uiMode
        )
        if (uiMode == NORMAL) {
            Spacer(Modifier.height(25.dp))
        }
    }
    ExplorerEditPopup(uiMode)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExplorerContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    links: List<Link>,
    scrollState: LazyListState = rememberLazyListState(),
    onLongPress: () -> Unit,
    uiMode: UIMode
) {
    val scope = rememberCoroutineScope()

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .padding(top=20.dp, start=25.dp, end=25.dp),
            state = scrollState
        ) {
            item {
                AnimatePopup(
                    visible = uiMode == ADD_LINK,
                    type = AnimationSpec.SLIDE_DOWN,
                ) {
                    CardLinkAdd()
                }
            }

            items(links) { link ->
                Spacer(Modifier.height(20.dp))
                CardLink(
                    modifier = Modifier.height(80.dp),
                    link = link,
                    onLongPress = onLongPress,
                    onIconClick = {
                        navController.navigate(
                            route = Screen.Content.route.plus("/${link.id}")
                        )
                    },
                    uiMode = uiMode
                )
            }
        }
    }
}

@Composable
fun ExplorerEditPopup(uiMode: UIMode) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatePopup(visible = (uiMode == UIMode.EDIT_LINK)) {
            AppBottomBarEditLink(
                modifier = Modifier,
                text = "샘플"
            )
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