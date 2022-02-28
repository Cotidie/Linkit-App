package com.apptive.linkit.presentation.component

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
import com.apptive.linkit._enums.UIMode
import com.apptive.linkit.domain.interfaces.IFolder
import com.apptive.linkit.domain.model.log
import com.apptive.linkit.presentation.model.FolderView
import com.apptive.linkit.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderGrid(
    modifier: Modifier = Modifier,
    uiMode: UIMode,
    folders: List<FolderView>,
    selected: FolderView,
    cells: Int,
    folderNameFocus: FocusRequester,
    onClick: (item: FolderView) -> Unit,
    onLongPress: (item: FolderView) -> Unit,
    onDismissRequest: (item: FolderView) -> Unit
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
                selected = (selected.id == folder.id) && uiMode.isEditMode(),
                focusRequester = folderNameFocus,
                onClick = { onClick(folder) },
                onLongPress = { onLongPress(folder) },
                onDismissRequest = { onDismissRequest(folder) }
            )
        }
    }
}