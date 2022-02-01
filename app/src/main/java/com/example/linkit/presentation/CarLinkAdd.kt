package com.example.linkit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit._constant.UIConstants
import com.example.linkit.presentation.component.IconTextButton
import com.example.linkit.presentation.component.IconTextButtonSmall

@Composable
fun CardLinkAdd(
    modifier : Modifier = Modifier,
    onAddClick : () -> Unit = {}
) {
    val radius = UIConstants.RADIUS_CARD
    var text by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 12.dp)
    ) {
        // 초기 상태 (링크 입력)
        HintArea()

        // 터치한 상태 (TextField)
        TextFieldArea(
            text = text,
            onTextChange = { text = it }
        )
    }
}

@Composable
private fun HintArea() {

}

@Composable
private fun TextFieldArea(
    text: String,
    onTextChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = text,
            singleLine = true,
            onValueChange = onTextChange
        )
        Spacer(Modifier.width(6.dp))
        IconTextButtonSmall(
            text = "추가",
            onClick = {},
            backgroundColor = Color.Gray
        )
    }
}

@Preview
@Composable
fun PreviewCardLinkAdd() {
    CardLinkAdd()
}