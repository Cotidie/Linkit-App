package com.example.linkit.data.network.api

import com.example.linkit.data.network.model.response.UserResponse
import retrofit2.http.GET

interface ILinkApi {
    @GET
    suspend fun getUserData() : List<UserResponse>
}