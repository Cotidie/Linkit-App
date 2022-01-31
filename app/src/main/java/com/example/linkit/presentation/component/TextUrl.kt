package com.example.linkit.presentation.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.example.linkit._constant.ColorConstants

@Composable
/** URL을 담는 텍스트. 클릭하면 브라우저를 열어 해당 링크로 이동한다. */
fun TextUrl(
    modifier: Modifier = Modifier,
    url: String
) {
    val uriHandler = LocalUriHandler.current
    val annotated = AnnotatedString(
        text = url,
        spanStyle = SpanStyle(
            color = ColorConstants.LINK_UNVISITED,
            textDecoration = TextDecoration.Underline
        )
    )

    ClickableText(
        modifier = modifier,
        text = annotated,
        onClick = { uriHandler.openUri(url) }
    )
}