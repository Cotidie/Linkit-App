package com.example.linkit.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit._constant.UIConstants
import com.example.linkit.domain.model.User
import com.example.linkit.presentation.component.AppBottomBar
import com.example.linkit.presentation.component.CardProfile
import com.example.linkit.presentation.component.IconTextButton

@Composable
fun ProfileScreen(navController: NavController) {
    // 뷰모델이나 싱글톤에서 가져오기
    val user = User("", 0, "daily142857@gmail.com", "원석")
    val guest = User.GUEST

    Scaffold(
        topBar = { Spacer(Modifier.height(UIConstants.HEIGHT_APP_BAR)) },
        bottomBar = { AppBottomBar() }
    ) { innerPadding ->
        // 배경영역: 배경색, 하단 패딩 설정
        ProfileBackgroundArea(innerPadding) {
            // 컨텐츠 영역: 배경 위에서 기본 패딩 설정
            ProfileContentArea {
                CardProfile(user = user)
                Divider(
                    modifier = Modifier.padding(vertical = 40.dp),
                    color = Color.LightGray,
                    thickness = 3.dp
                )
                ButtonsArea()
            }
        }
    }
}

@Composable
fun ProfileBackgroundArea(
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
fun ProfileContentArea(
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
fun ButtonsArea() {
    val minWidth = UIConstants.WIDTH_BUTTON_LONG

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(25.dp))
        IconTextButton(
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth),
            text = "버전정보",
            onClick = { /*TODO*/ }
        )
        Spacer(Modifier.height(15.dp))
        IconTextButton(
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth),
            text = "PUSH 알림",
            onClick = { /*TODO*/ }
        )
        Spacer(Modifier.height(15.dp))
        IconTextButton(
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth),
            text = "공지사항",
            onClick = { /*TODO*/ }
        )
        Spacer(Modifier.height(15.dp))
        IconTextButton(
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth),
            text = "친구관리",
            onClick = { /*TODO*/ }
        )
    }
}

@Preview
@Composable
fun PreviewProfile() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}