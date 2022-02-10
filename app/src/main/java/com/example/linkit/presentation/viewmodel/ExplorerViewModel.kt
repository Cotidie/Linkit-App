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
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExplorerViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
    private val folderRepo: FolderRepository
): ViewModel() {
    val links = mutableStateOf(emptyList<ILink>())
    val selected = ArrayList<ILink>()
    val currentFolder: MutableStateFlow<IFolder?> = MutableStateFlow(IFolder.DEFAULT)

    init { collectLinks(); "ExplorerViewModel 생성!".log() }

    /** 화면을 비운다. */
    fun clearScreen() {
        currentFolder.update { null }
    }

    fun setCurrentFolder(id: Long) {
        runBlocking {
            currentFolder.update {
                folderRepo.getFolder(id)
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
        viewModelScope.launch(Dispatchers.IO) {
            linkRepo.addLink(link)
        }
    }

    /** 선택된 링크들을 삭제한다. */
    fun deleteLinks() {
        viewModelScope.launch(Dispatchers.IO) {
            "삭제할 링크: $selected".log()
            linkRepo.deleteLinks(selected)
            clearSelect()
        }
    }

    /** 편집할 링크를 선택한다. */
    fun select(link: ILink) {
        selected.add(link)
        "추가한 링크: $link".log()
    }

    /** 편집할 링크 목록에서 제외한다. */
    fun unselect(link: ILink) {
        selected.remove(link)
        "선택 취소됨! 취소한 링크: $link".log()
    }

    /** 선택된 링크 목록을 비운다. */
    fun clearSelect() { selected.clear() }

    /** 현재 폴더가 변경되면 자동으로 폴더 내의 링크들을 불러온다. */
    private fun collectLinks() {
        viewModelScope.launch {
            currentFolder.filterNotNull()
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
        if (folder == null) return ILink.EMPTY

        return Link(parentFolder = folder.id, url = url)
    }
}