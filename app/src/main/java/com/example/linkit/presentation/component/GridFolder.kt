package com.example.linkit.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.linkit.domain.interfaces.IFolder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderGrid(
    modifier: Modifier = Modifier,
    folders: List<IFolder>,
    cells: Int,
    onClick: (item: IFolder) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(cells),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(folders) { folder ->
            FolderCard(
                folder = folder,
                onClick = { onClick(folder) }
            )
        }
    }
}