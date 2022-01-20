package com.example.linkit.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkit.data.repository.LinkRepository
import com.example.linkit.data.repository.UserRepository
import com.example.linkit.data.room.entity.Link
import com.example.linkit.domain.model.User
import com.example.linkit.data.storage.LinkItPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefs : LinkItPrefs,
    private val repository: LinkRepository
): ViewModel() {
    val user = prefs.getLoggedInUser()
    // 내부의 값을 변경할 수 있는 hot flow (StateFlow)
    private val _linkList = MutableStateFlow<List<Link>>(emptyList())
    var flag = false
    val linkList = _linkList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // distinctUntilChanged: 다른 값을 emit 할 때만 collect한다.
            repository.getAllLinks().distinctUntilChanged()
                .collect { linkList ->
                    if (linkList.isNullOrEmpty()) {
                        Log.d("Empty", ": Empty List")
                    } else {
                        _linkList.value = linkList
                    }
                }
        }
    }

    fun addLink(link: Link) = viewModelScope.launch { repository.addLink(link) }
    fun updateLink(link: Link) = viewModelScope.launch { repository.updateLink(link) }
    fun removeLink(link: Link) = viewModelScope.launch { repository.deleteLink(link) }

    fun save() {
        CoroutineScope(Dispatchers.IO).launch {
            var user = User("token", 123456L, "daily142857", "Wonseok")
            if (flag) user = User.GUEST
            prefs.setLoggedInUser(user)
        }
    }
}