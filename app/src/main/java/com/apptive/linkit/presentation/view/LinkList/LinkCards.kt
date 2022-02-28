package com.apptive.linkit.presentation.view.LinkList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apptive.linkit._enums.UIMode
import com.apptive.linkit.presentation.component.CardLink
import com.apptive.linkit.presentation.model.LinkView
import com.apptive.linkit.presentation.navigation.Screen

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LinkCards(
    navController: NavController,
    links: List<LinkView>,
    scrollState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        state = scrollState
    ) {
        items(links) { link ->
            Spacer(Modifier.height(20.dp))
            CardLink(
                modifier = Modifier.height(80.dp),
                link = link,
                onIconClick = {
                    navController.navigate(
                        route = Screen.Content.route.plus("/${link.id}")
                    )
                },
                uiMode = UIMode.NORMAL
            )
        }
    }
}