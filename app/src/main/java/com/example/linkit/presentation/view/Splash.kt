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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.linkit._constant.UIConstants
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.viewmodel.LoginViewModel
import com.example.linkit.ui.theme.CustomTypo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val duration = UIConstants.DURATION_ANIMATION_SPLASH
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(duration)
    )

    LaunchedEffect(key1 = true) {
        val nextScreen = when (loginViewModel.isLoggedIn()) {
            true -> Screen.Home
            false -> Screen.Login
        }
        startAnimation = true
        "다음화면 $nextScreen".log()
        "로그인된 유저 ${loginViewModel.currentUser}".log()

        // 화면 전환
        delay(duration + 500L)
        navController.popBackStack()
        navController.navigate(nextScreen.route)
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
private fun Splash(alpha: Float) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .alpha(alpha = alpha)
    ) {
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