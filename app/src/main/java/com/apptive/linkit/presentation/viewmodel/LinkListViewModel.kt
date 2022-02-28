package com.apptive.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptive.linkit._enums.SearchType
import com.apptive.linkit._enums.SortingOption
import com.apptive.linkit._enums.UIMode
import com.apptive.linkit.domain.interfaces.IFolder
import com.apptive.linkit.domain.interfaces.ILink
import com.apptive.linkit.domain.model.log
import com.apptive.linkit.domain.repository.LinkRepository
import com.apptive.linkit.presentation.mapper.LinkMapper
import com.apptive.linkit.presentation.model.LinkView
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
    val uiMode = mutableStateOf(UIMode.ALL_LINK)
    val sortBy = mutableStateOf(SortingOption.OLDEST)
    val searchBy = mutableStateOf(SearchType.URL)
    val searchText = mutableStateOf("")
    val displayLinks:List<LinkView> get() {
        val filtered = filterBySearchType(allLinks.value)

        return sortBySortingOption(filtered)
    }

    init { collectLinks() }

    private fun sortBySortingOption(target: List<LinkView>): List<LinkView> {
        return when (sortBy.value) {
            SortingOption.OLDEST -> target.sortedBy { it.created.value }
            SortingOption.NEWEST -> target.sortedByDescending { it.created.value }
        }
    }

    private fun filterBySearchType(target: List<LinkView>): List<LinkView> {
        if (searchText.value.isBlank()) return allLinks.value

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
