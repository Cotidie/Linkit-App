package com.example.linkit.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linkit.R
import com.example.linkit.ui.theme.Shapes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoogleLogin() {
    var isClicked by remember { mutableStateOf(false) }

    Surface(
        onClick = { isClicked = !isClicked },
        shape = Shapes.medium,
        color = MaterialTheme.colors.surface,
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .width(260.dp)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.padding(start = 30.dp, end = 20.dp),
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google Login Button",
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "구글 계정으로 로그인",
            )

            if (isClicked) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp, 16.dp),
                    strokeWidth = 2.dp,
                    color =MaterialTheme.colors.primary
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun GoogleLoginPreview() {
    GoogleLogin()
}