package com.example.linkit.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class NetworkHelper {
    var isConnected = true

    /** Retrofit API 호출에 대한 Wrapper. 통신 성공/실패를 판단한다. */
    suspend fun <T> handleApi(
        errorMessage: String = "Problem Fetching data at the moment!",
        call: suspend () -> Response<T>
    ): NetworkResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                isConnected = true
                response.body()?.let { body ->
                    return Success(body)
                }
            }
            response.errorBody()?.let {
                return try {
                    val errorString = it.string()
                    val errorObject = JSONObject(errorString)
                    Failure(errorObject.getString("status_message") ?: errorMessage)
                } catch (ignored: JsonSyntaxException) {
                    Failure(ignored.message)
                }
            }
            return Failure(errorMessage = errorMessage)
        } catch (e: Exception) {
            if (e is IOException) isConnected = false
            return Failure(e.message ?: errorMessage)
        }
    }

    /**
     * 현재 스마트폰이 인터넷과 연결되어 있는지 확인한다.
     * @param context Activity 컨텍스트
     * @return WIFI, LAN, 이동데이터와 연결되어 있으면 True
     */
    fun isNetworkAvailable(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}