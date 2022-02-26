package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.repository.LinkRepository
import com.example.linkit.presentation.mapper.LinkMapper
import com.example.linkit.presentation.model.LinkView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkListViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
    private val linkMapper: LinkMapper
) : ViewModel() {
    private val searchedLinks = MutableStateFlow(IFolder.DEFAULT)
    val links = mutableStateOf(emptyList<LinkView>())
    val uiMode = mutableStateOf(UIMode.ALL_LINK)

    fun collectLinks() {
        viewModelScope.launch {
            searchedLinks
                .flatMapLatest {
                    linkRepo.getAllLinks()
                }
                .distinctUntilChanged()
                .collect {
                    links.value = linkMapper.map(it)
                }
        }
    }
}
