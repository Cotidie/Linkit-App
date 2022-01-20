package com.example.linkit.network.api

import com.example.linkit.data.network.response.UserResponse
import retrofit2.http.GET

interface ILinkApi {
    @GET
    suspend fun getUserData() : List<UserResponse>
}