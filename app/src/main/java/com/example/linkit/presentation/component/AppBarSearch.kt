package com.example.linkit.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit.constant.UIConstants

@Composable
/** 검색 아이콘을 클릭했을 때 표시할 앱바 */
fun AppBarSearch(
    modifier : Modifier = Modifier,
    text : String,
    onClearClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {}
) {
    TopAppBar(
        modifier = modifier.height(UIConstants.HEIGHT_APP_BAR),
        title = {
            SearchTextField(
                modifier = Modifier
                    .padding(start= 10.dp, top= 12.dp, bottom= 12.dp, end= 20.dp),
                text = text,
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(UIConstants.ICON_SIZE_APP_BAR)
                            .alpha(ContentAlpha.medium),
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(UIConstants.ICON_SIZE_APP_BAR)
                            .alpha(ContentAlpha.medium)
                            .clickable { onClearClicked() },
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                },
                onTextChange = { onTextChange(it) }
            )
        },
        backgroundColor = Color.White,
        elevation = UIConstants.ELEVATION_APP_BAR
    )
}

@Preview
@Composable
fun PreviewAppBarSearch() {
    AppBarSearch(text = "", onClearClicked = {})
}
