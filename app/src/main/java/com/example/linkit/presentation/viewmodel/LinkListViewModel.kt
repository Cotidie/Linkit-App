package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit._enums.SortingOption
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.repository.LinkRepository
import com.example.linkit.presentation.mapper.LinkMapper
import com.example.linkit.presentation.model.LinkView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkListViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
    private val linkMapper: LinkMapper
) : ViewModel() {
    val links = mutableStateOf(emptyList<LinkView>())
    val uiMode = mutableStateOf(UIMode.ALL_LINK)
    val sortBy = mutableStateOf(SortingOption.OLDEST)

    init { collectLinks() }

    private fun collectLinks() {
        viewModelScope.launch(Dispatchers.IO) {
            linkRepo.getAllLinks()
                .distinctUntilChanged()
                .map { linkMapper.map(it) }
                .collect {
                    links.value = it
                }
        }
    }
}
