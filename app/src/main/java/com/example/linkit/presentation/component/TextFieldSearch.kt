package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit._constant.UIConstants

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable RowScope.() -> Unit,
    onSearchClicked: (String) -> Unit = {},
    onTextChange: (String) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp)),
        color = Color.LightGray,
        contentColor = Color.Transparent,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                leadingIcon()
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = { onTextChange(it) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = { onSearchClicked(text) }
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                trailingIcon()
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchTextField() {
    SearchTextField(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        text = "",
        leadingIcon = {
            Icon(
                modifier = Modifier.size(UIConstants.SIZE_ICON_MEDIUM),
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.size(UIConstants.SIZE_ICON_MEDIUM),
                imageVector = Icons.Filled.Clear,
                contentDescription = null
            )
        }
    )
}
