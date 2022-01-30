package com.example.linkit.presentation

import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit.R
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit._enums.UIMode
import com.example.linkit.presentation.component.*
import com.example.linkit.presentation.model.IconText
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.ui.theme.LinkItTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(navController: NavController) {
    val folders = getFolderSamples()
    var uiMode by remember { mutableStateOf(UIMode.NORMAL) }

    Scaffold(
        topBar = { AppBarHome() },
        bottomBar = {
            AppBottomBar(
                uiMode = uiMode,
                backHandler = {
                    if (uiMode != UIMode.NORMAL) uiMode = UIMode.NORMAL
                    else navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
        ) {
            // '개인폴더' 등 선택옵션 드롭다운 영역
            DropDownArea()
            // 폴더 표시 영역
            FolderGrid(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 25.dp),
                folders = folders,
                cells = 3,
                onClick = { folder ->
                    navController.navigate(
                        Screen.Explorer.route.plus("/${folder.name}")
                    )
                },
                onLongPress = { uiMode = UIMode.EDIT_FOLDER }
            )
            // '+ 폴더추가' 버튼 영역
            FolderAddArea(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .height(80.dp),
                uiMode = uiMode
            )
        }
    }
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
fun FolderAddArea(
    modifier: Modifier = Modifier,
    uiMode: UIMode
) {
    if (uiMode == UIMode.NORMAL) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            IconTextButton(
                text = "폴더 추가",
                icon = Icons.Filled.Add,
                shape = CircleShape,
                onClick = { /*TODO*/ },
                colors = buttonColors(Color.White),
                contentPadding = PaddingValues(20.dp, 8.dp)
            )
        }
    }
}

@Composable
fun getFolderSamples() : List<IFolder> {
    return listOf(
        FolderPrivate(1, "취미", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(2, "운동", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(3, "공부", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(4, "대학", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(5, "취업", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(6, "놀이", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(1, "취미", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(2, "운동", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(3, "공부", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(4, "대학", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(5, "취업", getBitmap(R.drawable.ic_sample_image_001)),
        FolderPrivate(6, "놀이", getBitmap(R.drawable.ic_sample_image_001))
    )
}

@Composable
fun getBitmap(id: Int) : Bitmap? {
    return AppCompatResources.getDrawable(cxt(), id)?.toBitmap()
}

@Preview
@Composable
fun PreviewHome() {
    val navController = rememberNavController()

    LinkItTheme {
        Home(navController)
    }
}