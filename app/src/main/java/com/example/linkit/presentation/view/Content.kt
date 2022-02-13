package com.example.linkit.presentation.view

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit._constant.ColorConstants
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.Link
import com.example.linkit.presentation.component.AppBottomBar
import com.example.linkit.presentation.component.CustomChip
import com.example.linkit.presentation.component.TextUrl
import com.example.linkit.presentation.viewmodel.ContentViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ContentScreen(
    navController: NavController,
    linkId: Long
) {
    val viewModel = hiltViewModel<ContentViewModel>()
    val link by viewModel.link
    var memo by remember {mutableStateOf("샘플메모")}
    var uiMode by remember {mutableStateOf(UIMode.NORMAL)}

    LaunchedEffect(linkId) {
        viewModel.setLink(linkId)
    }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                navController = navController,
                uiMode = uiMode
            )
        }
    ) { innerPadding ->
        ScreenBackground(innerPadding) {
            ScreenContent {
                HeadBarArea(
                    onCompleteClick = {
                        navController.popBackStack()
                    }
                )
                ImageArea(
                    image = link.image
                )
                Spacer(Modifier.height(10.dp))

                URLArea(link = link)
                Spacer(Modifier.height(10.dp))

                TagArea(link = link)
                Spacer(Modifier.height(10.dp))

                MemoArea(
                    modifier = Modifier.fillMaxSize(),
                    memo = memo,
                    onTextChange = {memo = it}
                )
            }
        }
    }
}

@Composable
private fun ScreenBackground(
    innerPadding: PaddingValues,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(ColorConstants.BACKGROUND_LIGHT),
        content = content
    ) 
}

@Composable
private fun ScreenContent(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 20.dp),
        content = content
    )
}

/** 공유유저들 표시와 '완료'버튼이 들어갈 자리 */
@Composable
private fun HeadBarArea(
    onCompleteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(UIConstants.HEIGHT_APP_BAR)
    ) {
        // 공유유저 정보
        /* Fill Here */

        // '완료' 버튼
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onCompleteClick
        ) {
            Icon(
                modifier = Modifier.size(UIConstants.SIZE_ICON_MEDIUM),
                imageVector = Icons.Filled.Done,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ImageArea(
    image: Bitmap
) {
    val maxHeight = UIConstants.HEIGHT_MAX_CONTENT_IMAGE

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(0.dp, maxHeight)
                .clip(RoundedCornerShape(10)),
            bitmap = image.asImageBitmap(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
private fun URLArea(
    link: ILink
) {
    TextUrl(
        modifier = Modifier,
        url = link.url.getString(true)
    )
}

@Composable
private fun TagArea(
    link: ILink
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        mainAxisSpacing = 15.dp
    ) {
        for (tag in link.tags) {
            CustomChip(text = "# $tag")
        }
        CustomChip(text = "+ 태그추가")
    }
}

@Composable
private fun MemoArea(
    modifier : Modifier = Modifier,
    memo: String,
    onTextChange: (String) -> Unit,
) {
    Box(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .fillMaxSize(),
            value = memo,
            onValueChange = onTextChange
        )
    }
}

@Preview
@Composable
fun PreviewContentScreen() {
    val navController = rememberNavController()
    val link = Link()
    ContentScreen(navController, 5)
}