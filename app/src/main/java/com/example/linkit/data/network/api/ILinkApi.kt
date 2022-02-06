package com.example.linkit.data.network.api

import com.example.linkit.data.network.model.response.UserResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ILinkApi {
    @GET
    suspend fun getUserData() : List<UserResponse>

    @GET
    suspend fun fetchUrl(@Url urlString: String) : Response<ResponseBody>
}