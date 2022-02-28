package com.apptive.linkit.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apptive.linkit._constant.UIConstants
import com.apptive.linkit.ui.theme.LinkItTheme

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    iconColor: Color = Color.White,
    text: String,
    textColor: Color = Color.White,
    onClick: () -> Unit,
    shape: Shape = MaterialTheme.shapes.small,
    cornerRadius: Dp = 0.dp,
    colors: ButtonColors = buttonColors(Color.Black),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    Button(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = cornerRadius,
                    topEnd = cornerRadius,
                    bottomStart = cornerRadius,
                    bottomEnd = cornerRadius
                )
            ),
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

@Composable
fun IconTextButtonSmall(
    modifier : Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    backgroundColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    val height = UIConstants.HEIGHT_BUTTON_SMALL
    val innerPadding = UIConstants.PADDING_BUTTON_SLIM

    IconTextButton(
        modifier = modifier.height(height),
        icon = icon,
        text = text,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        contentPadding = innerPadding
    )
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