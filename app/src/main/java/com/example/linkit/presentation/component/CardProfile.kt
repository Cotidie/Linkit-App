package com.example.linkit.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit._constant.UIConstants
import com.example.linkit.domain.model.User

@Composable
fun CardProfile(
    modifier: Modifier = Modifier,
    user: User,
    onGuestClick: () -> Unit = {}
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
                    .size(UIConstants.SIZE_ICON_HUGE),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null
            )
        }

        Spacer(Modifier.width(9.dp))

        if (user.isGuest())
            UserInfoGuest(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                onClick = onGuestClick
            )
        else
            UserInfoLoggedIn(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                user = user
            )
    }
}

@Composable
fun UserInfoLoggedIn(
    modifier: Modifier = Modifier,
    user: User
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(UIConstants.SIZE_ICON_TINY),
                imageVector = Icons.Filled.BakeryDining,
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(text = user.name)
        }
        Text(
            modifier = Modifier.weight(1f),
            text = user.email
        )
    }
}

@Composable
fun UserInfoGuest(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.clickable { onClick() },
                text = "로그인 하러가기",
                textDecoration = TextDecoration.Underline
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                modifier = Modifier
                    .size(UIConstants.SIZE_ICON_TINY),
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewCardProfile() {
    val user = User("", "", "daily142857@gmail.com", "원석")
    val guest = User.GUEST
    CardProfile(user = user)
}