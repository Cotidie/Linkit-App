package com.example.linkit.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.ui.theme.CustomTypo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(3000)
    )

    // 화면에 Fade 효과를 주고 3.5초 대기 후 홈 화면으로 전환
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3500)
        navController.popBackStack()
        navController.navigate(Screen.Login.route)
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .alpha(alpha = alpha)
    ) {
//        LinkItTitle(title = "LinkIt", subTitle = "link repository", color = Color.White, modifier = Modifier.padding())
        Text(
            text = "LinkIt",
            color = Color.White,
            style = CustomTypo.logo,
            modifier = Modifier
                .padding(top = 140.dp)
                .height(95.dp)
        )
        Text(
            text = "link repository",
            color = Color.White,
            style = MaterialTheme.typography.subtitle1,
        )
    }
}