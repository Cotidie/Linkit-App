package com.apptive.linkit.presentation.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apptive.linkit.R
import com.apptive.linkit._constant.UIConstants
import com.apptive.linkit._enums.UIMode
import com.apptive.linkit.domain.model.EMPTY_BITMAP
import com.apptive.linkit.domain.model.EMPTY_LONG
import com.apptive.linkit.domain.model.Url
import com.apptive.linkit.domain.model.isEmpty
import com.apptive.linkit.presentation.getBitmap
import com.apptive.linkit.presentation.longPress
import com.apptive.linkit.presentation.model.LinkView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardLink(
    modifier: Modifier = Modifier,
    link: LinkView,
    onCheckClick: (Boolean) -> Unit = {},
    onLongPress: (LinkView) -> Unit = {},
    onIconClick: () -> Unit = {},
    uiMode: UIMode
) {
    val radius = UIConstants.RADIUS_CARD
    val imageBackground =
        if (link.favicon.value.isEmpty()) Color.LightGray
        else                              Color.Transparent
    var selected by remember { mutableStateOf(false) }

    LaunchedEffect(uiMode) {
        if (!uiMode.isEditMode())
            selected = false
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(Color.White)
            .padding(start = 15.dp, top = 12.dp, bottom = 12.dp)
            .longPress(key=link) {
                selected = true
                onLongPress(link)
            }
    ) {
        Image(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(55.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(imageBackground)
                .combinedClickable {  },
            bitmap = link.favicon.value.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        LinkAndTags(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            link = link
        )
        IconOrCheckbox(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .padding(end = 8.dp),
            uiMode = uiMode,
            onIconClick = onIconClick,
            checked = selected && uiMode.isEditMode(),
            onCheckClick = {
                onCheckClick(it)
                selected = it
            }
        )
    }
}

@Composable
fun LinkAndTags(
    modifier: Modifier,
    link: LinkView
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(start=5.dp)
                .weight(1f)
                .fillMaxWidth(),
            text = link.url.value.getFullUrlString(false),
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1
        )
        LazyRow(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            items(items = link.tags) { tag ->
                CustomChip(prefix = "#", text = tag)
            }
        }
    }
}

@Composable
fun IconOrCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    uiMode: UIMode,
    onIconClick: () -> Unit = {},
    onCheckClick: (Boolean) -> Unit = {}
) {
    if (uiMode.isEditMode()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckClick,
                colors = CheckboxDefaults.colors(Color.Black)
            )
        }
    } else {
        Icon(
            modifier = modifier.clickable { onIconClick() },
            imageVector = Icons.Filled.ArrowForwardIos,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PreviewLinkCard() {
    val sampleImage = getBitmap(id = R.drawable.ic_sample_image_001)
    val link = remember {
        LinkView(
            0,
            parentFolder= EMPTY_LONG,
            mutableStateOf("네이버"),
            mutableStateOf("네이버캐스트"),
            mutableStateOf(Url("https://www.naver.com")),
            mutableStateOf(sampleImage),
            mutableStateOf(sampleImage),
            arrayListOf("유명", "네이버", "검색")
        )
    }
    
    CardLink(
        modifier = Modifier.height(80.dp),
        link = link,
        onLongPress = {},
        onCheckClick = {},
        uiMode = UIMode.EDIT_LINK
    )
}