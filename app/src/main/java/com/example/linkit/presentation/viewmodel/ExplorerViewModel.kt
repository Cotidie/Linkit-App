package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkit.data.repository.LinkRepository
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.*
import com.example.linkit.presentation.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExplorerViewModel @Inject constructor(
    private val repository: LinkRepository
): ViewModel() {
    val links = mutableStateOf(emptyList<ILink>())
    val currentFolder = MutableStateFlow(EMPTY_LONG)

    init { collectLinks() }

    fun addLink(urlString: String) {
        val url = Url(urlString)
        if (!url.isValid()) {
            "부정확한 URL입니다.".toast()
            return
        }
        val link = createNewLink(url)
        viewModelScope.launch { repository.addLink(link) }
    }

    /** 현재 폴더가 변경되면 자동으로 폴더 내의 링크들을 불러온다. */
    private fun collectLinks() {
        viewModelScope.launch {
            currentFolder
                .flatMapLatest { folderId ->
                    repository.getLinksInFolder(folderId)
                }
                .distinctUntilChanged()
                .collect {
                    links.value = it
                }
        }
    }

    /** url을 받아 Link 객체를 만든다. */
    private fun createNewLink(url: Url) : ILink {
        val folderId = currentFolder.value

        return Link(parentFolder = folderId, url = url)
    }
}