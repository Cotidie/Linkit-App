package com.apptive.linkit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.apptive.linkit.domain.repository.UserRepository
import com.apptive.linkit.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {
    private val user = userRepo.getLoggedInUser()

    fun getCurrentUser() : User = user
}