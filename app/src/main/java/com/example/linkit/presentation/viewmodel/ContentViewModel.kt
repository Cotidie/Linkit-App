package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.domain.repository.LinkRepository
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.Link
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val linkRepo: LinkRepository
): ViewModel() {
    private val _link = mutableStateOf(Link())
    val link: State<ILink> = _link

    /** DB에서 화면에 표시할 링크를 불러온다. */
    fun setLink(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            linkRepo.
        }
    }
}