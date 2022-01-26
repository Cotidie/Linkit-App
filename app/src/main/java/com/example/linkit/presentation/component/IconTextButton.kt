package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit.ui.theme.LinkItTheme

@Composable
fun IconTextButton(
    modifier: Modifier,
    icon: ImageVector? = null,
    iconColor: Color = Color.White,
    text: String,
    textColor: Color = Color.White,
    onClick: () -> Unit,
    colors: ButtonColors
) {
    Button(
        modifier = modifier.padding(top=10.dp, start=10.dp, end=10.dp),
        onClick = onClick,
        colors = colors
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = icon,
                contentDescription = text,
                tint = iconColor
            )
        }
        Text(
            text = text,
            color = textColor
        )
    }
}

@Preview
@Composable
fun PreviewIconTextButton() {
    LinkItTheme {
        IconTextButton(
            modifier = Modifier.padding(10.dp),
            icon = Icons.Filled.Person,
            text = "공유하기",
            onClick = { /*TODO*/ },
            colors = buttonColors(backgroundColor = Color.Gray)
        )
    }
}