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
import com.example.linkit.R
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit.domain.model.cxt
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.component.*
import com.example.linkit.presentation.model.IconText
import com.example.linkit.ui.theme.LinkItTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home() {
//    val homeViewModel = hiltViewModel<HomeViewModel>()
//    val user = homeViewModel.user.collectAsState(initial = User.GUEST).value
    val dropItems = listOf(
        IconText(Icons.Filled.Person,"개인폴더"),
        IconText(Icons.Filled.Group, "공유폴더"),
        IconText(Icons.Filled.Groups, "모두")
    )
    val folders = getFolderSamples()

    Scaffold(
        topBar = {
            AppBar(
                modifier = Modifier.height(65.dp)
            )
        },
        bottomBar = {
            BottomBar(
                iconSize = 32.dp
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
        ) {
            // '개인폴더' 등 선택옵션 드롭다운 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                DropDownButton(items = dropItems)
            }
            // 폴더 표시 영역
            FolderGrid(
                modifier = Modifier
                    .weight(6.6f)
                    .padding(start = 25.dp, end = 25.dp),
                folders = folders,
                cells = 3
            )
            // '+ 폴더추가' 버튼 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(innerPadding),
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
    LinkItTheme {
        Home()
    }
}