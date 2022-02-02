package com.example.linkit.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.linkit._constant.UIConstants

@Composable
fun DialogOk(
    show: Boolean = true,
    text: String = "",
    width: Dp = UIConstants.WIDTH_DIALOG,
    onDismissRequest: () -> Unit,
) {
    if (show) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Card(
                modifier = Modifier.defaultMinSize(minWidth = width),
                shape = RoundedCornerShape(10),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black
                ),
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 20.dp, bottom = 3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = text)
                    Spacer(Modifier.height(10.dp))
                    ButtonBlack(
                        text = "확인",
                        onClick = onDismissRequest,
                        contentPadding = UIConstants.PADDING_BUTTON_SLIM
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialogOk() {
    var show by remember { mutableStateOf(true) }
    val width = UIConstants.WIDTH_DIALOG
    val text = "준비중입니다."

    Card(
        modifier = Modifier.defaultMinSize(minWidth = width),
        shape = RoundedCornerShape(10),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black
        ),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(top=20.dp, bottom=3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text)
            Spacer(Modifier.height(10.dp))
            ButtonBlack(
                text = "확인",
                onClick = {},
                contentPadding = UIConstants.PADDING_BUTTON_SLIM
            )
        }
    }
}