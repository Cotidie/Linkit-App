package com.example.linkit.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit.MainActivity
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.component.*
import com.example.linkit.presentation.model.IconText
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.view.Home.HomeBottomButtonArea
import com.example.linkit.presentation.viewmodel.HomeViewModel
import com.example.linkit.ui.theme.LinkItTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    var selected by remember { mutableStateOf<IFolder>(IFolder.DEFAULT) }
    var uiMode by remember { mutableStateOf(UIMode.NORMAL) }
    val folderNameFocus = remember { FocusRequester() }

    // 편집 모드에서는 일반 모드로 돌아와야 한다.
    BackHandler {
        val prevScreen = navController.previousBackStackEntry?.destination

        if (uiMode == UIMode.NORMAL) {
            if (prevScreen == null) {
                MainActivity.instance.finish()
            } else {
                navController.popBackStack()
            }
        } else {
            uiMode = UIMode.NORMAL
        }
    }

    Scaffold(
        topBar = { AppBarHome(navController) },
        bottomBar = {
            AppBottomBar(
                navController = navController,
                uiMode=uiMode
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.Black)
        ) {
            // '개인폴더' 등 선택옵션 드롭다운 영역
            DropDownArea()

            // 폴더 표시 영역
            FolderGrid(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 25.dp),
                uiMode = uiMode,
                folders = viewModel.allFolders.value,
                selected = selected,
                cells = 3,
                folderNameFocus = folderNameFocus,
                onClick = { folder ->
                    navController.navigate(
                        Screen.Explorer.route.plus("/${folder.name}")
                    )
                },
                onLongPress = { folder ->
                    selected = folder
                    uiMode = UIMode.EDIT_FOLDER
                },
                onDismissRequest = { folder ->
                    uiMode = UIMode.NORMAL
                    viewModel.updateFolder(folder)
                }
            )

            // '+ 폴더추가 / 완료' 버튼 영역
            HomeBottomButtonArea(
                uiMode = uiMode,
                onAddClick = {
                    uiMode = UIMode.EDIT_FOLDER
                    viewModel.addFolder(name = "신규폴더")
                },
                onCompleteClick = {
                    uiMode = UIMode.NORMAL
                    viewModel.updateFolder(folder = selected)
                }
            )
        }
    }

    HomeEditPopup(
        uiMode = uiMode,
        onRenameClick = { uiMode = UIMode.RENAME_FOLDER }
    )
}

/** Content 영역 중 상단의 드롭다운 버튼 영역*/
@Composable
fun DropDownArea() {
    val dropItems = listOf(
        IconText(Icons.Filled.Person,"개인폴더"),
        IconText(Icons.Filled.Group, "공유폴더"),
        IconText(Icons.Filled.Groups, "모두")
    )
    var selected by remember { mutableStateOf(dropItems[0]) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        DropDownButton(
            expanded = expanded,
            items = dropItems,
            button = {
                IconTextButton(
                    modifier = Modifier
                        .size(145.dp, 50.dp)
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp),
                    icon = selected.icon,
                    iconColor = Color.White,
                    text = selected.text,
                    textColor = Color.White,
                    onClick = { expanded = !expanded },
                    colors = buttonColors(backgroundColor = Color.Gray)
                )
            },
            item = { index, item ->
                if (item.hasIcon()) {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        imageVector = item.icon!!,
                        contentDescription = item.text
                    )
                }
                Text(text = item.text)
            },
            onItemClick = { item ->
                selected = item
                expanded = false
            },
            onDismissRequest = { expanded = false }
        )
    }
}

@Composable
private fun HomeEditPopup(
    uiMode: UIMode,
    onDeleteClick: () -> Unit = {},
    onRenameClick: () -> Unit = {},
    onReimageClick: () -> Unit = {}
) {
    val launcher = chooseImageLauncher {
        "$it".log()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatePopup(
            visible = (uiMode == UIMode.EDIT_FOLDER)
        ) {
            AppBottomBarEditFolder(
                text = "샘플",
                onDeleteClick = onDeleteClick,
                onRenameClick = onRenameClick,
                onReimageClick = { launcher.launch("image/*")  }
            )
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    val navController = rememberNavController()

    LinkItTheme {
        Home(navController)
    }
}