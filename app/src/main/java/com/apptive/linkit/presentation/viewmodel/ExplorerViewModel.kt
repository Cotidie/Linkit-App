package com.apptive.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptive.linkit._enums.SortingOption.*
import com.apptive.linkit._enums.UIMode
import com.apptive.linkit.domain.repository.FolderRepository
import com.apptive.linkit.domain.repository.LinkRepository
import com.apptive.linkit.domain.interfaces.IFolder
import com.apptive.linkit.domain.interfaces.ILink
import com.apptive.linkit.domain.model.Link
import com.apptive.linkit.domain.model.Url
import com.apptive.linkit.domain.model.log
import com.apptive.linkit.presentation.mapper.LinkMapper
import com.apptive.linkit.presentation.model.LinkView
import com.apptive.linkit.presentation.toast
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
    private val folderRepo: FolderRepository,
    private val linkMapper: LinkMapper
): ViewModel() {
    private val _links = mutableStateOf(emptyList<LinkView>())
    val uiMode = mutableStateOf(UIMode.NORMAL)
    val selected = ArrayList<LinkView>()
    val sortBy = mutableStateOf(OLDEST)
    val currentFolder: MutableStateFlow<IFolder?> = MutableStateFlow(IFolder.DEFAULT)
    val links: List<LinkView> get() {
        return when (sortBy.value) {
            NEWEST -> _links.value.sortedBy { it.created.value }
            OLDEST -> _links.value.sortedByDescending { it.created.value }
        }
    }

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
            val linkToDelete = linkMapper.map(selected)
            linkRepo.deleteLinks(linkToDelete)
            clearSelect()
        }
    }

    /** 편집할 링크를 선택한다. */
    fun select(link: LinkView) {
        selected.add(link)
    }

    /** 편집할 링크 목록에서 제외한다. */
    fun unselect(link: LinkView) {
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
                    _links.value = linkMapper.map(it)
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