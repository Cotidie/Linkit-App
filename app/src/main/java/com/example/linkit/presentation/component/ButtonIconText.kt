package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit.ui.theme.LinkItTheme

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    iconColor: Color = Color.Black,
    text: String,
    textColor: Color = Color.Black,
    onClick: () -> Unit,
    shape: Shape = MaterialTheme.shapes.small,
    colors: ButtonColors = buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding
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