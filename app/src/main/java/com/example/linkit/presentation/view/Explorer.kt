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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linkit._constant.ColorConstants
import com.example.linkit._enums.AnimationSpec
import com.example.linkit._enums.UIMode
import com.example.linkit._enums.UIMode.*
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.*
import com.example.linkit.presentation.component.*
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.viewmodel.ExplorerViewModel
import kotlinx.coroutines.launch

/**
 * UI는 다음 두 단계로 구성된다. 또한 private 컴포넌트는 ViewModel, NavController를 전달받는다.
 *   - Background: 배경색 설정
 *   - Content: 공통 여백 설정
 */

@Composable
fun Explorer(
    navController: NavController,
    viewModel: ExplorerViewModel,
    folderId: Long
) {
    val currentFolder by viewModel.currentFolder.collectAsState()
    var uiMode by viewModel.uiMode
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
                folderName = currentFolder?.name ?: "",
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
        ExplorerBackground(innerPadding) {
            ExplorerContent(
                navController = navController,
                viewModel = viewModel,
                scrollState = scrollState,
            )
        }
    }
    ExplorerEditPopup(
        uiMode = uiMode,
        onDelete = {
            viewModel.deleteLinks()
            uiMode = NORMAL
        }
    )
}

@Composable
private fun ExplorerBackground(
    padding: PaddingValues,
    content: @Composable BoxScope.() -> Unit
) {
    BackgroundArea(
        innerPadding = padding,
        color = Color.DarkGray,
        content = content
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ExplorerContent(
    navController: NavController,
    viewModel: ExplorerViewModel,
    scrollState: LazyListState = rememberLazyListState(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var uiMode by viewModel.uiMode
    val links by viewModel.links

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
                onCheckClick = { check ->
                    "대상 링크 $link".log()
                    if (check)   viewModel.select(link)
                    else         viewModel.unselect(link)
                },
                onLongPress = {
                    uiMode = EDIT_LINK
                    "대상 링크 $link".log()
                    viewModel.select(link)
                },
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
fun ExplorerEditPopup(
    uiMode: UIMode,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatePopup(visible = (uiMode == EDIT_LINK)) {
            PopupEditLink(
                modifier = Modifier,
                text = "링크 편집",
                onDelete = onDelete
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
                index.toLong(), EMPTY_LONG, s, "", url, EMPTY_BITMAP, tags
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