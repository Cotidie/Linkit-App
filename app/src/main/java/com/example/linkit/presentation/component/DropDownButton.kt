package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.ButtonDefaults.buttonColors
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.model.IconText
import com.example.linkit.ui.theme.LinkItTheme

@Composable
fun DropDownButton(
    modifier: Modifier = Modifier,
    items: List<IconText>
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(items[0]) }

    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.CenterEnd)
        .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        IconTextButton(
            modifier = Modifier.size(145.dp, 50.dp),
            icon = selected.icon,
            text = selected.text,
            onClick = { expanded = !expanded },
            colors = buttonColors(backgroundColor = Color.Gray)
        )
        DropdownMenu(
            modifier = Modifier.width(140.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.size(150.dp, 40.dp),
                    onClick = {
                        selected = items[index]
                        expanded = false
                    }
                ) {
                    if (item.hasIcon()) {
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            imageVector = item.icon!!,
                            contentDescription = item.text
                        )
                    }
                    Text(text = item.text)
                }
            }
        }
    }
}