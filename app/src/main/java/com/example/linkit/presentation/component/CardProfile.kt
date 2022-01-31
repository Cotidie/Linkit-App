package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit._constant.UIConstants

@Composable
fun CardProfile(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(UIConstants.SIZE_ICON_MEDIUM_LARGE),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null
            )
        }

        Spacer(Modifier.width(9.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(UIConstants.SIZE_ICON_TINY),
                    imageVector = Icons.Filled.BakeryDining,
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "원석")
            }
            Text(text = "daily142857@gmail.com")
        }
    }
}

@Preview
@Composable
fun PreviewCardProfile() {
    CardProfile()
}