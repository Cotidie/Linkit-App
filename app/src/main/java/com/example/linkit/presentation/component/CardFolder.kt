package com.example.linkit.presentation.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit.R
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.UIMode
import com.example.linkit._enums.UIMode.*
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.getBitmap
import com.example.linkit.ui.theme.LinkItTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CardFolder(
    folder: IFolder,
    uiMode: UIMode,
    selected: Boolean,
    focusRequester: FocusRequester = FocusRequester(),
    onClick: () -> Unit = {},
    onLongPress: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    // Composition 스코프에서 호출
    LaunchedEffect(uiMode) {
        if (selected && uiMode == RENAME_FOLDER)
            focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .combinedClickable(
                enabled = !uiMode.isEditMode() || selected,
                onClick = onClick,
                onLongClick = onLongPress
            )
            .background(
                if (selected)
                    Color.LightGray.copy(alpha = 0.2f)
                else
                    Color.Transparent
            )
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min)
            .border(
                width = if (selected) 1.dp else (-1).dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        ImageWithCheckbox(
            modifier = Modifier
                .size(UIConstants.SIZE_IMAGE_FOLDER),
            image = folder.image.asImageBitmap(),
            selected = selected && uiMode == EDIT_FOLDER,
            onCheckedChange = { checked ->
                if (!checked) {
                    onDismissRequest()
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(vertical = 10.dp)
        ) {
            // 폴더명
            BasicTextField(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = 6.dp)
                    .focusRequester(focusRequester),
                enabled = (selected && uiMode == RENAME_FOLDER),
                value = text,
                textStyle = TextStyle(color=Color.White),
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDismissRequest()
                        keyboardController?.hide()
                    }
                ),
                onValueChange = { text = it }
            )
            // 공유폴더 표시 아이콘
            if (folder.isShared()) {
                Icon(
                    modifier = Modifier.scale(0.6f),
                    imageVector = Icons.Filled.Group,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            // 더보기 아이콘
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun ImageWithCheckbox(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    image: ImageBitmap
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            bitmap = image,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        if (selected) {
            Checkbox(
                modifier = Modifier
                    .align(Alignment.TopStart),
                checked = selected,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(Color.Black)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFolderCard() {
    val folder = FolderPrivate(
        id = 5,
        name = "개인폴더",
        image = getBitmap(id = R.drawable.ic_sample_image_001)
    )
    val uiMode = UIMode.EDIT_FOLDER

    LinkItTheme {
        Box(modifier = Modifier.background(Color.Black)) {
            CardFolder(folder, uiMode)
        }
    }
}