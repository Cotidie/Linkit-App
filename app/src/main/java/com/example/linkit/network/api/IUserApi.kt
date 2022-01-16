package com.example.linkit.network.api

import com.example.linkit.data.network.request.GoogleLoginRequest
import com.example.linkit.data.network.response.ApiResponse
import com.example.linkit.data.network.response.GoogleLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 유저 정보와 관련된 로직을 제공하는 API
 */
interface IUserApi {
    /** 구글 서버로부터 받은 토큰을 통해 로그인을 진행한다. */
    @POST("login/google")
    fun signInGoogle(
        @Body gLogin: GoogleLoginRequest
    ) : Response<ApiResponse<GoogleLoginResponse>>
}