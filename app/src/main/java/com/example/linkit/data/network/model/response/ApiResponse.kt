package com.example.linkit.data.network.response

data class ApiResponse<T>(
    val success: Boolean,
    val status: Int,
    val msg: String,
    val res_data: T
)