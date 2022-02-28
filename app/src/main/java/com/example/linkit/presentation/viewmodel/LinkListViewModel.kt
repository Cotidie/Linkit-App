package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit._enums.SearchType
import com.example.linkit._enums.SortingOption
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.log
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
    private val allLinks = mutableStateOf(emptyList<LinkView>())
    val displayLinks = mutableStateOf(emptyList<LinkView>())
    val uiMode = mutableStateOf(UIMode.ALL_LINK)
    val sortBy = mutableStateOf(SortingOption.OLDEST)
    val searchBy = mutableStateOf(SearchType.URL)
    val searchText = mutableStateOf("")

    init { collectLinks() }

    fun searchLinks() {
        val textToSearch = searchText.value
        if (textToSearch.isBlank()) {
            displayLinks.value = allLinks.value; "값이 비어있음: ${searchText.value}".log()
            return
        }

        val filtered = filterBySearchType(allLinks.value)
        val sorted = sortBySortingOption(filtered)

        displayLinks.value = sorted
    }

    private fun sortBySortingOption(target: List<LinkView>): List<LinkView> {
        return when (sortBy.value) {
            SortingOption.OLDEST -> target.sortedBy { it.created.value }
            SortingOption.NEWEST -> target.sortedByDescending { it.created.value }
        }
    }

    private fun filterBySearchType(target: List<LinkView>): List<LinkView> {
        return target.filter { containsBySearchType(linkView = it) }
    }

    private fun containsBySearchType(linkView: LinkView): Boolean {
        val textToSearch = searchText.value

        return when (searchBy.value) {
            SearchType.URL -> linkView.containsInUrl(textToSearch)
            SearchType.TAG -> linkView.containsInTags(textToSearch)
        }
    }

    private fun collectLinks() {
        viewModelScope.launch(Dispatchers.IO) {
            linkRepo.getAllLinks()
                .distinctUntilChanged()
                .map { linkMapper.map(it) }
                .collect {
                    allLinks.value = it
                }
        }
    }
}
