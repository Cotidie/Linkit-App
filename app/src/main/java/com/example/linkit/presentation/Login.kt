package com.example.linkit.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.linkit.presentation.component.GoogleLogin
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.ui.theme.CustomTypo
import com.example.linkit.ui.theme.LinkItTheme

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
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .clickable { navController.navigate(Screen.Home.route) }
                ,
                text = "로그인 건너뛰기 >",
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()

    LinkItTheme {
        LoginScreen(navController)
    }
}