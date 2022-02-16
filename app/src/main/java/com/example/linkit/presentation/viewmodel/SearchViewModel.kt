package com.example.linkit.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.data.repository.LinkRepository
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
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

    fun collectLinks(searchUrl: String, folderId:Long) {
        viewModelScope.launch {
            searchedLinks
                .flatMapLatest {
                    linkRepo.searchLinkByUrl(searchUrl, folderId)
                }
                .distinctUntilChanged()
                .collect {
                    links.value = it
                }
        }
    }

}