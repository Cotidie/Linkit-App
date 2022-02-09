package com.example.linkit.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.linkit._enums.SearchBarState
import com.example.linkit._enums.SearchKey
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.model.Link
import com.example.linkit.domain.model.Url
import com.example.linkit.presentation.component.AppBarSearch
import com.example.linkit.presentation.component.CardLink
import com.example.linkit.presentation.viewmodel.SearchViewModel

@Composable
fun SearchResultScreen(navController: NavController, text: String) {
//    val links by viewModel.searchedLinks
    val links = listOf(
        Link(url = Url("www.naver.com")),
        Link(url = Url("www.nver.com")),
        Link(url = Url("www.navr.com")),
        Link(url = Url("www.naer.com"))
    )

    Scaffold(
        topBar = {
            AppBarSearch(
                modifier = Modifier.height(80.dp),
                text = "샘플",
                onClearClicked = {},
                onTextChange = {}
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            for (link in links) {
                CardLink(
                    modifier = Modifier.height(100.dp),
                    link = link,
                    uiMode = UIMode.NORMAL
                )
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
private fun SearchResultPreview() {
    val navController = rememberNavController()
    val links = listOf(
        Link(url = Url("www.naver.com")),
        Link(url = Url("www.nver.com")),
        Link(url = Url("www.navr.com")),
        Link(url = Url("www.naer.com"))
    )
    SearchResultScreen(navController, text = "naver")
}