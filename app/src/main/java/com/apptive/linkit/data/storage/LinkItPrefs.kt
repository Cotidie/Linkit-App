package com.apptive.linkit.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.apptive.linkit.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore("settings")

/** Primitive 자료형에 대해 Key-Value Pair로 저장하는 간단한 저장소 */
class LinkItPrefs @Inject constructor(@ApplicationContext context: Context) {
    private val linkItStore = context.dataStore

    suspend fun setLoggedInUser(user: User) {
        linkItStore.edit { storeMap ->
            storeMap[LOGGED_IN_USER_ID] = user.id
            storeMap[LOGGED_IN_USER_EMAIL] = user.email
            storeMap[LOGGED_IN_USER_NAME] = user.name
            storeMap[LOGGED_IN_USER_TOKEN] = user.token
        }
    }

    fun getLoggedInUser() : Flow<User> {
        return linkItStore.data.map { storeMap ->
            val id = storeMap[LOGGED_IN_USER_ID] ?: User.GUEST.id
            val email = storeMap[LOGGED_IN_USER_EMAIL] ?: User.GUEST.email
            val name = storeMap[LOGGED_IN_USER_NAME] ?: User.GUEST.name
            val token = storeMap[LOGGED_IN_USER_TOKEN] ?: User.GUEST.token

            User(id = id, email = email, name = name, token = token)
        }
    }

    // 키값 정의
    companion object {
        private val LOGGED_IN_USER_ID = stringPreferencesKey("logged_in_user_id")
        private val LOGGED_IN_USER_EMAIL = stringPreferencesKey("logged_in_user_email")
        private val LOGGED_IN_USER_NAME = stringPreferencesKey("logged_in_user_name")
        private val LOGGED_IN_USER_TOKEN = stringPreferencesKey("logged_in_user_token")
    }
}