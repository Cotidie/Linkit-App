package com.apptive.linkit.data.network.model.response

import com.google.gson.annotations.SerializedName

/** 구글 로그인 요청 시 서버로부터 반환받을 자료형 */
data class GoogleLoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    val email: String,
    val name: String
)

data class UserResponse(
    val userId: Int,
    val Id: Int,
    val title: String,
    val completed: Boolean
)