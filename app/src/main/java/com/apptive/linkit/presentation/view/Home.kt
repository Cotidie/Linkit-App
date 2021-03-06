package com.apptive.linkit.presentation

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import com.apptive.linkit.MainActivity
import com.apptive.linkit._enums.UIMode
import com.apptive.linkit.domain.interfaces.IFolder
import com.apptive.linkit.presentation.component.*
import com.apptive.linkit.presentation.model.FolderView
import com.apptive.linkit.presentation.model.IconText
import com.apptive.linkit.presentation.navigation.Screen
import com.apptive.linkit.presentation.view.Home.HomeBottomButton
import com.apptive.linkit.presentation.viewmodel.HomeViewModel
import com.apptive.linkit.ui.theme.LinkItTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val folders = viewModel.folders
    var selected by remember { mutableStateOf(FolderView()) }
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
                folders = folders,
                selected = selected,
                cells = 3,
                folderNameFocus = folderNameFocus,
                onClick = { folder ->
                    navController.navigate(
                        Screen.Explorer.route.plus("/${folder.id}")
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
            HomeBottomButton(
                uiMode = uiMode,
                onAddClick = {
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
        folderName = selected.name.value,
        uiMode = uiMode,
        onRenameClick = { uiMode = UIMode.RENAME_FOLDER },
        onReimageClick = { bitmap ->
            selected.image.value = bitmap
            viewModel.updateFolder(selected)
        },
        onDeleteClick = {
            viewModel.deleteFolder(folder = selected)
        }
    )
}

/** Content 영역 중 상단의 드롭다운 버튼 영역*/
@Composable
private fun DropDownArea() {
    val dropItems = listOf(
        IconText(Icons.Filled.Person,"개인폴더"),
        IconText(Icons.Filled.Group, "공유폴더"),
        IconText(Icons.Filled.Groups, "모두")
    )
    var selected by remember { mutableStateOf(dropItems[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        DropDownButton(
            items = dropItems,
            button = { expand ->
                IconTextButton(
                    modifier = Modifier
                        .size(145.dp, 50.dp)
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp),
                    icon = selected.icon,
                    iconColor = Color.White,
                    text = selected.text,
                    textColor = Color.White,
                    onClick = expand,
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
            },
        )
    }

    if (selected.text == "공유폴더") {
        DialogOk(
            text = "준비중입니다.",
            onDismissRequest = { selected = dropItems[0] }
        )
    }
}

@Composable
private fun HomeEditPopup(
    folderName: String = "",
    uiMode: UIMode,
    onDeleteClick: () -> Unit = {},
    onRenameClick: () -> Unit = {},
    onReimageClick: (Bitmap) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatePopup(
            visible = (uiMode == UIMode.EDIT_FOLDER)
        ) {
            PopupEditFolder(
                text = folderName,
                onDeleteClick = onDeleteClick,
                onRenameClick = onRenameClick,
                onReimageClick = onReimageClick
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