package com.example.linkit.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.linkit.R
import com.example.linkit.domain.interfaces.IFolder

@Composable
fun FolderCard(folder: IFolder) {
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxSize(),
        elevation = 2.dp,
        shape = RoundedCornerShape(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp, 50.dp)
                    .clip(RoundedCornerShape(5.dp)),
                painter = painterResource(id = R.drawable.ic_sample_image_001),
                contentDescription = folder.name
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(folder.name)
            }
        }
    }
}