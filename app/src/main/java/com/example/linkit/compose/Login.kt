package com.example.linkit.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.linkit.compose.component.GoogleLogin
import com.example.linkit.ui.theme.CustomTypo

@ExperimentalMaterialApi
@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(2f)
        ) {
            Text(
                text = "LinkIt",
                color = Color.Black,
                style = CustomTypo.logo,
                modifier = Modifier
                    .padding(top = 100.dp)
                    .height(95.dp)
            )
            Text(
                text = "로그인",
                color = Color.Black,
                style = MaterialTheme.typography.subtitle1,
            )
        }
        Box(modifier = Modifier.weight(3f)) {
            TextButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = { /*TODO*/ }
            ) {
                GoogleLogin()
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = "로그인 건너뛰기 >",
                textDecoration = TextDecoration.Underline
            )
        }
    }
}