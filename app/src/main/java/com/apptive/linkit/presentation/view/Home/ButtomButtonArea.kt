package com.apptive.linkit.presentation.view.Home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apptive.linkit._enums.UIMode
import com.apptive.linkit.domain.model.FolderPrivate
import com.apptive.linkit.presentation.component.IconTextButton
import com.apptive.linkit.presentation.viewmodel.HomeViewModel

@Composable
fun HomeBottomButton(
    uiMode: UIMode,
    onAddClick: () -> Unit,
    onCompleteClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        if (uiMode.isEditMode()) {
            EditCompleteButton(onClick = onCompleteClick)
        } else {
            FolderAddButton(onClick = onAddClick)
        }
    }
}

@Composable
private fun FolderAddButton(
    onClick: () -> Unit = {}
) {
    IconTextButton(
        text = "폴더추가",
        textColor = Color.Black,
        icon = Icons.Filled.Add,
        iconColor = Color.Black,
        shape = CircleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.White),
        contentPadding = PaddingValues(20.dp, 8.dp)
    )
}

@Composable
private fun EditCompleteButton(
    onClick: () -> Unit
) {
    IconTextButton(
        text = "완료",
        textColor = Color.Black,
        icon = Icons.Filled.Check,
        iconColor = Color.Black,
        shape = CircleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.White),
        contentPadding = PaddingValues(20.dp, 8.dp)
    )
}