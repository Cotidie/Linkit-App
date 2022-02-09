package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.linkit.data.repository.LinkRepository
import com.example.linkit.domain.interfaces.ILink
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val linkRepo: LinkRepository
) : ViewModel() {
    val searchedLinks = mutableStateOf(emptyList<ILink>())


}