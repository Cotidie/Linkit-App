package com.example.linkit.presentation.view

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
import com.example.linkit.domain.model.Link
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.component.AppBottomBar
import com.example.linkit.presentation.component.CustomChip
import com.example.linkit.presentation.component.TextUrl
import com.example.linkit.presentation.model.LinkView
import com.example.linkit.presentation.view.Content.TagAddChip
import com.example.linkit.presentation.view.Content.TagInputChip
import com.example.linkit.presentation.viewmodel.ContentViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ContentScreen(
    navController: NavController,
    linkId: Long
) {
    val viewModel = hiltViewModel<ContentViewModel>()
    val linkView by viewModel.link
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
                        viewModel.saveLink()
                        navController.popBackStack()
                    }
                )
                ImageArea(linkView = linkView)
                Spacer(Modifier.height(10.dp))

                URLArea(linkView = linkView)
                Spacer(Modifier.height(10.dp))

                TagArea(viewModel = viewModel, linkView = linkView)
                Spacer(Modifier.height(10.dp))

                MemoArea(linkView = linkView)
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
    linkView: LinkView
) {
    val image by linkView.image
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
    linkView: LinkView
) {
    val url by linkView.url

    TextUrl(
        modifier = Modifier,
        url = url
    )
}

@Composable
private fun TagArea(
    viewModel: ContentViewModel,
    linkView: LinkView
) {
    var uiMode by viewModel.uiMode
    val tags = linkView.tags

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        mainAxisSpacing = 5.dp,
    ) {
        for (tag in tags) {
            CustomChip(text = "# $tag")
        }

        if (uiMode != UIMode.ADD_TAG)
            TagAddChip(
                onClick = {
                    uiMode = UIMode.ADD_TAG
                }
            )
        if (uiMode == UIMode.ADD_TAG)
            TagInputChip(
                onDone = { text ->
                    tags.add(text)
                    uiMode = UIMode.NORMAL
                }
            )

    }
}

@Composable
private fun MemoArea(
    linkView: LinkView
) {
    var memo by linkView.memo

    Box(modifier = Modifier.fillMaxSize()) {
        BasicTextField(
            modifier = Modifier
                .fillMaxSize(),
            value = memo,
            onValueChange = {memo = it},
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