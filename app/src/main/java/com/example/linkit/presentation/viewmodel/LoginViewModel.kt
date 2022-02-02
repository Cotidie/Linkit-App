package com.example.linkit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.linkit.LinkItApp
import com.example.linkit.MainActivity
import com.example.linkit.R
import com.example.linkit.data.repository.UserRepository
import com.example.linkit.domain.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo : UserRepository
) : ViewModel() {
    val currentUser: User = userRepo.getLoggedInUser()

    fun isLoggedIn() : Boolean = !currentUser.isGuest()

    private fun getGoogleLoginAuth(): GoogleSignInClient {
        val cxt = LinkItApp.cxt()
        val clientId = cxt.getString(R.string.google_client_ID)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(clientId)
            .requestId()
            .requestProfile()
            .build()

        return GoogleSignIn.getClient(cxt, gso)
    }
}