package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.repository.LinkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
) : ViewModel() {
    private val searchedLinks = MutableStateFlow(IFolder.DEFAULT)
    val links = mutableStateOf(emptyList<ILink>())

    //searchType을 받아서 URL로 검색할 건지, TAG로 검색할 건지 설정
    fun collectLinks(searchText: String, folderId: Long, searchType: String? = null) {
        viewModelScope.launch {
            searchedLinks
                .flatMapLatest {
                    searchLinks(searchText, folderId, searchType)
                }
                .distinctUntilChanged()
                .collect {
                    links.value = it
                }
        }
    }

    private fun searchLinks(searchText: String, folderId: Long, searchType: String?) =
        if (searchType == "URL") linkRepo.searchLinkByUrl(searchText,folderId)
        else linkRepo.searchLinkByTag(searchText, folderId)

}