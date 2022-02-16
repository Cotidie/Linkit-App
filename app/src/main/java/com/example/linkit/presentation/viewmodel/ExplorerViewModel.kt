package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.data.repository.FolderRepository
import com.example.linkit.data.repository.LinkRepository
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.Link
import com.example.linkit.domain.model.Url
import com.example.linkit.presentation.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
/**
 * 폴더 내 링크들을 표시하는 Explorer.kt와 연결된 뷰모델
 * @property allLinks 현재 폴더에 존재하는 모든 링크들
 * @property links Explorer 쪽에 표시할 링크들
 */
class ExplorerViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
    private val folderRepo: FolderRepository,
) : ViewModel() {
    private var allLinks : List<ILink> = emptyList()
    val links = mutableStateOf(emptyList<ILink>())
    val currentFolder = MutableStateFlow(IFolder.DEFAULT)

    init {
        collectLinks()
    }

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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                linkRepo.addLink(link)
            }
        }
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
    private fun createNewLink(url: Url): ILink {
        val folder = currentFolder.value
        return Link(parentFolder = folder.id, url = url)
    }
}