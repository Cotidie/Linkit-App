package com.example.linkit.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderGrid(
    modifier: Modifier = Modifier,
    uiMode: UIMode,
    folders: List<IFolder>,
    selected: IFolder,
    cells: Int,
    folderNameFocus: FocusRequester,
    onClick: (item: IFolder) -> Unit,
    onLongPress: (item: IFolder) -> Unit,
    onDismissRequest: (item: IFolder) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(cells),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(folders) { folder ->
            CardFolder(
                folder = folder,
                uiMode = uiMode,
                selected = (selected == folder) && uiMode.isEditMode(),
                focusRequester = folderNameFocus,
                onClick = { onClick(folder) },
                onLongPress = { onLongPress(folder) },
                onDismissRequest = { onDismissRequest(folder) }
            )
        }
    }
}