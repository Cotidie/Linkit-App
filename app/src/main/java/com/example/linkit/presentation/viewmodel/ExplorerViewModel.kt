package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.data.repository.FolderRepository
import com.example.linkit.data.repository.LinkRepository
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.*
import com.example.linkit.presentation.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExplorerViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
    private val folderRepo: FolderRepository
): ViewModel() {
    val links = mutableStateOf(emptyList<ILink>())
    val currentFolder = MutableStateFlow(IFolder.DEFAULT)

    init { collectLinks() }

    fun clearScreen() {
        viewModelScope.launch {
            currentFolder.update { IFolder.DEFAULT }
        }
    }

    fun setCurrentFolder(id: Long) {
        runBlocking {
            withContext(Dispatchers.IO) {
                currentFolder.update {
                    folderRepo.getFolder(id)
                }
            }
        }
    }

    fun addLink(urlString: String) {
        val url = Url(urlString)
        if (!url.isValid()) {
            "부정확한 URL입니다.".toast()
            return
        }
        val link = createNewLink(url)
        viewModelScope.launch { linkRepo.addLink(link) }
    }

    /** 현재 폴더가 변경되면 자동으로 폴더 내의 링크들을 불러온다. */
    private fun collectLinks() {
        viewModelScope.launch {
            currentFolder
                .flatMapLatest { folder ->
                    linkRepo.getLinksInFolder(folder.id)
                }
                .distinctUntilChanged()
                .collect {
                    links.value = it
                }
        }
    }

    /** url을 받아 Link 객체를 만든다. */
    private fun createNewLink(url: Url) : ILink {
        val folder = currentFolder.value

        return Link(parentFolder = folder.id, url = url)
    }
}