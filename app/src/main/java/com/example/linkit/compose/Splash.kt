package com.example.linkit.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Text(
            text = "LinkIt",
            color = Color.White,
            fontFamily
            fontWeight = FontWeight.ExtraBold,
            fontSize = 60.sp,
            modifier = Modifier.padding(top = 100.dp)
        )
        Text(
            text = "link repository",
            color = Color.White
        )
    }
}