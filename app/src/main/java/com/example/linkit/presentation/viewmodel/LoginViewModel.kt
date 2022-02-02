package com.example.linkit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.linkit.data.repository.UserRepository
import com.example.linkit.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo : UserRepository
) : ViewModel() {
    val currentUser: User = userRepo.getLoggedInUser()

    fun isLoggedIn() : Boolean = !currentUser.isGuest()
}