package com.example.linkit.presentation.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit.R
import com.example.linkit._constant.UIConstants
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit.presentation.getBitmap
import com.example.linkit.presentation.longPress
import com.example.linkit.ui.theme.LinkItTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardFolder(
    folder: IFolder,
    uiMode: UIMode,
    onClick: () -> Unit,
    onLongPress: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress
            )
            .background(Color.Transparent),
    ) {
        ImageWithCheckbox(
            modifier = Modifier
                .size(UIConstants.SIZE_IMAGE_FOLDER),
            uiMode = uiMode,
            image = folder.image!!.asImageBitmap()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(vertical = 10.dp)
        ) {
            // 폴더명
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                text = folder.name,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // 공유폴더 표시 아이콘
            if (!folder.isShared()) {
                Icon(
                    modifier = Modifier.scale(0.6f),
                    imageVector = Icons.Filled.Group,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            // 더보기 아이콘
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(bottom = 2.dp),
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun ImageWithCheckbox(
    modifier: Modifier = Modifier,
    uiMode: UIMode,
    image: ImageBitmap
) {
    var checked by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            bitmap = image,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        if (uiMode.isEditMode()) {
            Checkbox(
                modifier = Modifier
                    .align(Alignment.TopStart),
                checked = checked,
                onCheckedChange = { checked = !checked },
                colors = CheckboxDefaults.colors(Color.Black)
            )
        }
    }
}

@Preview
@Composable
fun PreviewFolderCard() {
    val folder = FolderPrivate(
        id = 5,
        name = "개인폴더",
        image = getBitmap(id = R.drawable.ic_sample_image_001)
    )
    val uiMode = UIMode.EDIT_FOLDER

    LinkItTheme {
        CardFolder(folder, uiMode, {}, {})
    }
}