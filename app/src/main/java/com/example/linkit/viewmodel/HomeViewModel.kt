package com.example.linkit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.domain.model.User
import com.example.linkit.storage.LinkItPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefs : LinkItPrefs
): ViewModel() {
    val user = prefs.getLoggedInUser()
    var flag = false

    fun save() {
        CoroutineScope(Dispatchers.IO).launch {
            var user = User("token", 123456L, "daily142857", "Wonseok")
            if (flag) user = User.GUEST
            prefs.setLoggedInUser(user)
        }
    }
}