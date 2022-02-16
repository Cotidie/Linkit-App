package com.example.linkit.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit._enums.UIMode
import com.example.linkit.domain.model.log
import com.example.linkit.domain.repository.LinkRepository
import com.example.linkit.presentation.mapper.LinkMapper
import com.example.linkit.presentation.model.LinkView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
    private val linkMapper: LinkMapper
): ViewModel() {
    private val _link = mutableStateOf(LinkView())
    val link: State<LinkView> = _link
    var uiMode = mutableStateOf(UIMode.NORMAL)

    /** DB에서 화면에 표시할 링크를 불러온다. */
    fun setLink(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val domain = linkRepo.getLink(id)
            _link.value = linkMapper.map(domain)
        }
    }

    /** 현재 링크를 DB에 반영한다. */
    fun saveLink() {
        viewModelScope.launch(Dispatchers.IO) {
            val domain = linkMapper.map(_link.value)
            linkRepo.updateLink(domain)
        }
    }

    override fun onCleared() {
        "ContentViewModel 제거!".log()
        super.onCleared()
    }
}