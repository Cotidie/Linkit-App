package com.example.linkit.data.repository

import com.example.linkit.data.network.api.ILinkApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ILinkApi
) {

}