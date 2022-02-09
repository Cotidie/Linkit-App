package com.example.linkit.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit._enums.AnimationSpec
import com.example.linkit._enums.UIMode
import com.example.linkit._enums.UIMode.*
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.*
import com.example.linkit.presentation.component.*
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.viewmodel.ExplorerViewModel
import kotlinx.coroutines.launch

@Composable
fun Explorer(
    navController: NavController,
    viewModel: ExplorerViewModel,
    folderId: Long
) {
    val currentFolder by viewModel.currentFolder.collectAsState()
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

    LaunchedEffect(folderId) {
        viewModel.setCurrentFolder(folderId)
    }

    Scaffold(
        topBar = {
            AppBarExplorer(
                folderName = currentFolder.name,
                navController = navController
            )
        },
        bottomBar = {
            AppBottomBar(
                navController = navController,
                uiMode = uiMode,
                onAddClick = {
                    uiMode = ADD_LINK
                    scope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(innerPadding)
        ) {
            ExplorerContent(
                navController = navController,
                viewModel = viewModel,
                links = viewModel.links.value.toList(),
                scrollState = scrollState,
                onLongPress = { uiMode = EDIT_LINK },
                uiMode = uiMode
            )
        }
    }
    ExplorerEditPopup(uiMode)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ExplorerContent(
    navController: NavController,
    viewModel: ExplorerViewModel,
    links: List<ILink>,
    scrollState: LazyListState = rememberLazyListState(),
    onLongPress: () -> Unit,
    uiMode: UIMode
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
                CardLinkAdd(
                    onAddClick = { urlString ->
                        keyboardController?.hide()
                        viewModel.addLink(urlString)
                    }
                )
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

@Composable
fun ExplorerEditPopup(uiMode: UIMode) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatePopup(visible = (uiMode == EDIT_LINK)) {
            AppBottomBarEditLink(
                modifier = Modifier,
                text = "링크 편집"
            )
        }
    }
}

@Composable
fun getSampleLinks() : List<Link> {
    val titles = listOf("네이버", "다음","네이버", "다음","네이버", "다음")
    val url = Url("https://developer.android.com/jetpack/compose/layout?hl=ko")
    val tags = listOf("유명", "네이버", "검색")
    val links = ArrayList<Link>().apply {
        titles.forEachIndexed { index, s ->
            add(Link(
                index.toLong(), EMPTY_LONG, s, "", url, EMPTY_BITMAP, EMPTY_BITMAP, tags
            ))
        }
    }

    return links
}

//@Preview
//@Composable
//fun PreviewExplorer() {
//    val navController = rememberNavController()
//
//    Explorer(navController, EMPTY_LONG)
//}