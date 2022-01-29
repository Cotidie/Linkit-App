package com.example.linkit.data.network

sealed class NetworkResult<out T>
data class Success<out T>(val data: T) : NetworkResult<T>()
data class Failure(val errorMessage: String?) : NetworkResult<Nothing>()