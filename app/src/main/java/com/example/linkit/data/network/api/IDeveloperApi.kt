package com.example.linkit.data.network.api

import com.example.linkit.data.network.model.response.ApiResponse
import retrofit2.Response
import retrofit2.http.POST

/** 개발자 전용 API */
interface IDeveloperApi {
    @POST
    suspend fun clearDatabase() : Response<ApiResponse<Unit>>
}