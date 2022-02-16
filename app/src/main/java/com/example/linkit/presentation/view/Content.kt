package com.example.linkit.presentation.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit.R
import com.example.linkit._constant.ColorConstants
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.model.EMPTY_LONG
import com.example.linkit.domain.model.Link
import com.example.linkit.domain.model.Url
import com.example.linkit.presentation.component.AppBottomBar
import com.example.linkit.presentation.component.CustomChip
import com.example.linkit.presentation.component.TextUrl
import com.example.linkit.presentation.getBitmap
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ContentScreen(
    navController: NavController,
    linkId: Long
) {
    val bitmap = getBitmap(id = R.drawable.ic_sample_image_001)
    val link by remember {
        mutableStateOf(
            Link(
                linkId,
                parentFolder = EMPTY_LONG,
                "링크",
                "오늘은 낚시를 갔다. 물고기를 많이 잡았다. 뿌듯했다.",
                Url("https://www.google.com/search?q=jetpack+compose+maxsize&newwindow=1&hl=en&biw=1396&bih=656&sxsrf=APq-WBsnuSZEUIwVd7jld09SJvANZoqdGw%3A1643644748914&ei=TAf4Yb2ON83yhwOd9J2QDA&ved=0ahUKEwi95JTrrdz1AhVN-WEKHR16B8IQ4dUDCA8&uact=5&oq=jetpack+compose+maxsize&gs_lcp=Cgdnd3Mtd2l6EAMyBQghEKABMgUIIRCgAToECCMQJzoFCAAQkQI6BQgAEIAEOgUILhCABDoECC4QQzoLCC4QgAQQxwEQ0QM6BAgAEEM6CgguEMcBENEDEEM6BQgAEMsBOgYIABAWEB5KBAhBGABKBAhGGABQAFjUFGCVFWgAcAJ4AIABmwGIAZUWkgEEMC4yMZgBAKABAcABAQ&sclient=gws-wiz"),
                bitmap,
                bitmap,
                listOf("낚시", "밤낚시", "물고기")
            )
        )
    }
    var memoString by remember { mutableStateOf("오늘은 낚시를 갔다. 물고기를 많이 잡았다. 뿌듯했다.")}
    var uiMode by remember {mutableStateOf(UIMode.NORMAL)}

    Scaffold(
        bottomBar = {
            AppBottomBar(
                navController = navController,
                uiMode = uiMode
            )
        }
    ) { innerPadding ->
        ContentBackgroundArea(innerPadding) {
            ContentContentArea {
                ContentHeadArea(
                    onCompleteClick = {
                        navController.popBackStack()
                    }
                )
                // 이미지 영역
                ImageArea(
                    image = link.favicon.asImageBitmap()
                )

                Spacer(Modifier.height(10.dp))

                // URL 영역
                TextUrl(
                    modifier = Modifier,
                    url = link.url.getString(true)
                )

                Spacer(Modifier.height(10.dp))

                // 태그 영역
                FlowRow(
                    mainAxisSpacing = 15.dp
                ) {
                    for (tag in link.tags) {
                        CustomChip(text = "# $tag")
                    }
                }

                Spacer(Modifier.height(10.dp))

                // 메모 영역
                MemoArea(
                    modifier = Modifier.fillMaxSize(),
                    memo = memoString,
                    onTextChange = {memoString = it}
                )
            }
        }
    }

}

@Composable
fun ContentBackgroundArea(
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
fun ContentContentArea(
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
fun ContentHeadArea(
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
fun ImageArea(
    image: ImageBitmap
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
            bitmap = image,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun MemoArea(
    modifier : Modifier = Modifier,
    memo: String,
    onTextChange: (String) -> Unit
) {
    Box(modifier = modifier) {
        BasicTextField(
            modifier = Modifier.fillMaxSize(),
            value = memo,
            onValueChange = onTextChange
        )
    }
}

@Preview
@Composable
fun PreviewContentScreen() {
    val navController = rememberNavController()
    val link = Link.empty()
    ContentScreen(navController, 5)
}