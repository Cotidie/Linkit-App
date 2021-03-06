package com.apptive.linkit.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apptive.linkit._constant.UIConstants
import com.apptive.linkit._enums.SearchType
import com.apptive.linkit.presentation.model.Size

@Composable
/** 검색 아이콘을 클릭했을 때 표시할 앱바 */
fun AppBarSearch(
    modifier : Modifier = Modifier,
    text : String,
    searchType: SearchType = SearchType.URL,
    onClearClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onSearchTypeChange: (SearchType) -> Unit = {}
) {
    TopAppBar(
        modifier = modifier.height(UIConstants.HEIGHT_APP_BAR),
        title = {
            SearchTextField(
                modifier = Modifier
                    .padding(start = 10.dp, top = 12.dp, bottom = 12.dp, end = 20.dp),
                text = text,
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(UIConstants.SIZE_ICON_MEDIUM)
                            .alpha(ContentAlpha.medium),
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    SearchTypeDropDown(
                        searchType = searchType,
                        onTypeChange = onSearchTypeChange
                    )
                    Spacer(Modifier.width(5.dp))
                    Icon(
                        modifier = Modifier
                            .size(UIConstants.SIZE_ICON_MEDIUM)
                            .alpha(ContentAlpha.medium)
                            .clickable { onClearClicked() },
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                },
                onTextChange = { onTextChange(it) },
                onSearchClicked = { onSearchClicked() }
            )
        },
        backgroundColor = Color.White,
        elevation = UIConstants.ELEVATION_APP_BAR
    )
}

@Composable
private fun SearchTypeDropDown(
    searchType: SearchType = SearchType.URL,
    onTypeChange: (SearchType) -> Unit = {}
) {
    val types = SearchType.values().toList()

    DropDownButton(
        items = types,
        button = { expand ->
           CustomChip(
               text = "  ${searchType.text}  ",
               textColor = Color.Black,
               textStyle = MaterialTheme.typography.subtitle2,
               borderWidth = 1.dp,
               borderColor = Color.DarkGray,
               height = 30.dp,
               onClick = expand
           )
        },
        item = { index, item ->
            val isSelected = (item == searchType)
            Text(
                text = item.text,
                fontWeight =
                    if (isSelected) FontWeight.ExtraBold
                    else            null
            )
        },
        itemSize = Size(60.dp, 30.dp),
        onItemClick = { type ->
            onTypeChange(type)
        }
    )
}

@Preview
@Composable
fun PreviewAppBarSearch() {
    AppBarSearch(text = "", onClearClicked = {})
}
