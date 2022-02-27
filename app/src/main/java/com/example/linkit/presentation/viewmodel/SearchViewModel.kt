package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit._enums.SearchType
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.EMPTY_LONG
import com.example.linkit.domain.model.log
import com.example.linkit.domain.repository.LinkRepository
import com.example.linkit.presentation.mapper.LinkMapper
import com.example.linkit.presentation.model.LinkView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
    private val linkMapper: LinkMapper
) : ViewModel() {
    val searchedLinks = mutableStateOf(emptyList<LinkView>())
    val parentFolderId = mutableStateOf(EMPTY_LONG)
    val searchType = mutableStateOf(SearchType.URL)
    val searchText = mutableStateOf("")

    fun setParentFolder(id: Long?) {
        val folderId = id ?: EMPTY_LONG
        parentFolderId.value = folderId
    }

    fun setSearchType(typeText: String?) {
        val type: SearchType

        if (typeText == null) type = SearchType.URL
        else                  type = SearchType.of(typeText)

        searchType.value = type
    }

    fun setSearchText(text: String?) {
        if (text == null) return

        searchText.value = text
    }

    fun searchLinks() {
        viewModelScope.launch(Dispatchers.IO) {
            val links: List<ILink>
            val searchText = searchText.value
            val parentFolder = parentFolderId.value

            when (searchType.value) {
                SearchType.URL -> { links = linkRepo.getLinksByUrl(searchText, parentFolder) }
                SearchType.TAG -> { links = linkRepo.getLinksByTag(searchText, parentFolder) }
            }

            searchedLinks.value = linkMapper.map(links)
        }
    }

    override fun onCleared() {
        "SearchViewModel 제거!".log()
        super.onCleared()
    }
}