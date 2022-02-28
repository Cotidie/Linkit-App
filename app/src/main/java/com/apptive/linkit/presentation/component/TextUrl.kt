package com.apptive.linkit.presentation.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.apptive.linkit._constant.ColorConstants
import com.apptive.linkit.domain.model.Url

@Composable
/** URL을 담는 텍스트. 클릭하면 브라우저를 열어 해당 링크로 이동한다. */
fun TextUrl(
    modifier: Modifier = Modifier,
    url: Url,
    maxLines: Int = 1
) {
    val uriHandler = LocalUriHandler.current
    val urlString = url.getFullUrlString(showProtocol = true)
    val annotated = AnnotatedString(
        text = urlString,
        spanStyle = SpanStyle(
            color = ColorConstants.LINK_UNVISITED,
            textDecoration = TextDecoration.Underline
        )
    )

    ClickableText(
        modifier = modifier,
        text = annotated,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onClick = { uriHandler.openUri(urlString) }
    )
}