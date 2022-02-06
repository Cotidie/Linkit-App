package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkit.data.repository.LinkRepository
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.EMPTY_BITMAP
import com.example.linkit.domain.model.Link
import com.example.linkit.domain.model.Url
import com.example.linkit.domain.model.log
import com.example.linkit.presentation.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExplorerViewModel @Inject constructor(
    private val repository: LinkRepository
): ViewModel() {
    val links = mutableStateListOf<ILink>()
    val currentFolder = mutableStateOf(IFolder.DEFAULT)
    val testBitmap = mutableStateOf(EMPTY_BITMAP)

    fun addLink(urlString: String) {
        val url = Url(urlString)
        if (!url.isValid()) {
            "부정확한 URL입니다.".toast()
            return
        }
        val link = createNewLink(url)
        viewModelScope.launch { repository.addLink(link) }
    }

    /** 링크 목록을 담은 Flow에 연결한다. 화면 내비게이션 시 실행한다. */
    fun collectLinks(id: Long) {
        viewModelScope.launch {

        }
    }

    /** url을 받아 Link 객체를 만든다. */
    private fun createNewLink(url: Url) : ILink {
        val folderId = currentFolder.value.id

        return Link(parentFolder = folderId, url = url)
    }
}