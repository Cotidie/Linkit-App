package com.apptive.linkit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.apptive.linkit.LinkItApp
import com.apptive.linkit.R
import com.apptive.linkit.domain.repository.UserRepository
import com.apptive.linkit.domain.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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

    fun loginUserGoogle(account: GoogleSignInAccount) {
        val user = User(
            token = account.idToken!!,
            id = account.id!!,
            email = account.email!!,
            name = account.displayName!!
        )

        userRepo.setLoggedInUser(user)
    }

    fun getGoogleLoginAuth(): GoogleSignInClient {
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