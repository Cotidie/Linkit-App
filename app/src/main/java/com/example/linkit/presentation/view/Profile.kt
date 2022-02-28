package com.example.linkit.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit._constant.UIConstants
import com.example.linkit.domain.model.User
import com.example.linkit.presentation.component.*
import com.example.linkit.presentation.component.IconTextButton
import com.example.linkit.presentation.model.Dialog
import com.example.linkit.presentation.navigation.Screen
import com.example.linkit.presentation.viewmodel.ProfileViewModel

private enum class Dialogs {
    NONE, VERSION, PUSH, NOTICE, FRIEND
}

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    var type by remember { mutableStateOf(Dialogs.NONE) }

    Scaffold(
        topBar = { Spacer(Modifier.height(UIConstants.HEIGHT_APP_BAR)) },
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->
        // 배경영역: 배경색, 하단 패딩 설정
        ProfileBackgroundArea(innerPadding) {
            // 컨텐츠 영역: 배경 위에서 기본 패딩 설정
            ProfileContentArea {
                CardProfile(
                    user = viewModel.getCurrentUser(),
                    onGuestClick = {
                        navController.navigate(Screen.Login.route)
                    }
                )
                Divider(
                    modifier = Modifier.padding(vertical = 40.dp),
                    color = Color.LightGray,
                    thickness = 3.dp
                )
                ButtonsArea(
                    onClick = { type = it }
                )
            }

            // 하단바 구분선
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .align(Alignment.BottomCenter),
                color = Color.LightGray
            )
        }
    }

    // 다이얼로그
    DialogArea(
        type = type,
        onDismissRequest = { type = Dialogs.NONE }
    )
}

@Composable
private fun ProfileBackgroundArea(
    innerPadding: PaddingValues = PaddingValues(),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White),
        content = content
    )
}

@Composable
private fun ProfileContentArea(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 20.dp),
        content = content
    )
}

@Composable
private fun ButtonsArea(
    onClick: (type: Dialogs) -> Unit
) {
    val minWidth = UIConstants.WIDTH_BUTTON_LONG

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(25.dp))
        ButtonBlack(
            text = "버전정보",
            minWidth = minWidth,
            onClick = { onClick(Dialogs.VERSION) }
        )
        Spacer(Modifier.height(15.dp))
        ButtonBlack(
            text = "PUSH 알림",
            minWidth = minWidth,
            onClick = { onClick(Dialogs.PUSH) }
        )
        Spacer(Modifier.height(15.dp))
        ButtonBlack(
            text = "공지사항",
            minWidth = minWidth,
            onClick = { onClick(Dialogs.NOTICE) }
        )
        Spacer(Modifier.height(15.dp))
//        ButtonBlack(
//            text = "친구관리",
//            minWidth = minWidth,
//            onClick = { onClick(Dialogs.FRIEND) }
//        )
    }
}

@Composable
private fun DialogArea(
    type: Dialogs,
    onDismissRequest: () -> Unit
) {
    when (type) {
        Dialogs.VERSION -> {
            DialogOk(
                text = "ver 1.0.0",
                onDismissRequest = onDismissRequest
            )
        }
        Dialogs.NOTICE,
        Dialogs.PUSH,
        Dialogs.FRIEND -> {
            DialogOk(
                text = "준비중입니다.",
                onDismissRequest = onDismissRequest
            )
        }
        Dialogs.NONE -> {}
    }
}

@Preview
@Composable
fun PreviewProfile() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}