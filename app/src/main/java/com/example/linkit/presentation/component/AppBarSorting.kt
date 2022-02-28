package com.example.linkit.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.SortingOption

@Composable
fun AppBarSorting(
    title: @Composable () -> Unit = {},
    sortBy: SortingOption = SortingOption.OLDEST,
    onSearchClick: () -> Unit = {},
    onSortChange: (SortingOption) -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier.height(UIConstants.HEIGHT_APP_BAR),
        title = title,
        actions = {
            SearchIcon(onSearchClick = onSearchClick)
            SortingButton(
                currentSorting = sortBy,
                onItemClick = onSortChange
            )
            Spacer(Modifier.width(20.dp))
//            VertMoreIcon()
        },
        backgroundColor = Color.Transparent,
        elevation = 2.dp
    )
}

@Composable
private fun SearchIcon(
    onSearchClick: () -> Unit
) {
    Icon(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .size(UIConstants.SIZE_ICON_MEDIUM)
            .clickable { onSearchClick() },
        imageVector = Icons.Filled.Search,
        contentDescription = null,
        tint = Color.Black
    )
}

@Composable
private fun VertMoreIcon() {
    Icon(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .size(UIConstants.SIZE_ICON_MEDIUM),
        imageVector = Icons.Filled.MoreVert,
        contentDescription = null,
        tint = Color.Black
    )
}