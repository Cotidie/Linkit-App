package com.example.linkit.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit.R
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit.ui.theme.LinkItTheme

@Composable
fun FolderCard(
    folder: IFolder,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .background(Color.Transparent),
    ) {
        Box(modifier = Modifier.size(110.dp, 110.dp)) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
                painter = painterResource(id = R.drawable.ic_sample_image_001),
                contentScale = ContentScale.Crop,
                contentDescription = folder.name
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(vertical = 10.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start=10.dp),
                text = folder.name,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (!folder.isShared()) {
                Icon(
                    modifier = Modifier.scale(0.6f),
                    imageVector = Icons.Filled.Group,
                    contentDescription = null,
                    tint = Color.White
                )
            }
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

@Preview
@Composable
fun PreviewFolderCard() {
    LinkItTheme {
        Column(
            modifier = Modifier
                .clickable { }
                .background(Color.Transparent),
        ) {
            Box(modifier = Modifier.size(150.dp)) {
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_sample_image_001),
                    contentScale = ContentScale.Crop,
                    contentDescription = "aa"
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color.Transparent)
            ) {
                Text("aa", color = Color.White)
            }
        }
    }
}