package com.apptive.linkit.domain.repository

import com.apptive.linkit.data.storage.LinkItPrefs
import com.apptive.linkit.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/** 로그인된 유저, 공유 유저 등을 불러오는 리포지토리 */
@Singleton
class UserRepository @Inject constructor(
    private val prefs: LinkItPrefs
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getLoggedInUser() : User {
        return runBlocking {
            prefs.getLoggedInUser().first()
        }
    }
    fun setLoggedInUser(user: User) {
        scope.launch {
            prefs.setLoggedInUser(user)
        }
    }
}