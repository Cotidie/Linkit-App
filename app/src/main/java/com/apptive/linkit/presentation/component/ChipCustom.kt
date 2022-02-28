package com.apptive.linkit.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/** TODO: modifier는 하나만 두고, 배경색, 패딩 등 해당하는 값을 직접 받기 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomChip(
    text: String = "",
    textColor: Color = Color.Black,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    prefix: String = "",
    padding: PaddingValues = PaddingValues(horizontal = 6.dp),
    height: Dp = (21.5).dp,
    minWidth: Dp? = null,
    borderWidth: Dp = (-1).dp,
    borderColor: Color = Color.Black,
    backgroundColor: Color = Color.Transparent,
    write: Boolean = false,
    focusRequester: FocusRequester = FocusRequester(),
    onDone: (String) -> Unit = {},
    onClick: () -> Unit = {},
) {
    var writeText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .height(height)
            .then(
                if (minWidth != null) Modifier.defaultMinSize(minWidth = minWidth)
                else                  Modifier
            ),
        onClick = onClick,
        shape = CircleShape,
        color = backgroundColor,
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = prefix,
                color = textColor,
                style = textStyle
            )

            BasicTextField(
                modifier = Modifier
                    .defaultMinSize(minWidth = 1.dp)
                    .width(IntrinsicSize.Min)
                    .focusRequester(focusRequester),
                value =
                    if (write)  writeText
                    else        text,
                onValueChange = { writeText = it },
                textStyle = textStyle.copy(color=textColor),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDone(writeText)
                        writeText = ""
                    }
                ),
                enabled = write
            )
        }
    }
}

@Preview
@Composable
fun PreviewTagChip() {
    CustomChip(prefix = "#", text = "테스트", borderWidth = 1.dp)
}

