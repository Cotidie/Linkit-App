package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit._enums.SearchType
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.EMPTY_LONG
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
    val searchType = mutableStateOf(SearchType.URL)

    fun searchLinks(key: String, folderId: Long = EMPTY_LONG) {
        viewModelScope.launch(Dispatchers.IO) {
            val links: List<ILink>

            when (searchType.value) {
                SearchType.URL -> { links = linkRepo.getLinksByUrl(key, folderId) }
                SearchType.TAG -> { links = linkRepo.getLinksByTag(key, folderId) }
            }

            searchedLinks.value = linkMapper.map(links)
        }
    }
}