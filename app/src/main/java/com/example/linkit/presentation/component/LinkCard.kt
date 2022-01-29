package com.example.linkit.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit.R
import com.example.linkit.domain.model.Link
import com.example.linkit.presentation.getBitmap

@Composable
fun LinkCard(
    modifier: Modifier = Modifier,
    link: Link
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(13.dp))
            .background(Color.White)
            .padding(start = 15.dp, top = 12.dp, bottom = 12.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(end=15.dp)
                .size(55.dp)
                .clip(RoundedCornerShape(8.dp)),
            bitmap = link.image.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end=8.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = link.url,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1
            )
            // 넘치는 경우 처리
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (tag in link.tags) {
                    CustomChip(
                        modifier = Modifier.padding(end = 8.dp),
                        textModifier = Modifier.padding(horizontal = 3.dp),
                        text = "# $tag")
                }
            }
        }
        Icon(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end=8.dp),
            imageVector = Icons.Filled.ArrowForwardIos,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PreviewLinkCard() {
    val link = Link(
        0, 
        "네이버", 
        "네이버캐스트", 
        "https://www.naver.com", 
        getBitmap(id = R.drawable.ic_sample_image_001)!!,
        listOf("유명", "네이버", "검색")
    )
    
    LinkCard(
        modifier = Modifier.height(80.dp),
        link = link)
}