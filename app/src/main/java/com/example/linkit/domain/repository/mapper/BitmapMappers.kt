package com.example.linkit.domain.repository.mapper

import com.example.linkit.data.mapper.ResponseToBitmap
import com.example.linkit.data.mapper.StringToBitmap
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapMappers @Inject constructor(
    private val stringToBitmap: StringToBitmap,
    private val responseToBitmap: ResponseToBitmap
) {
    fun map(string: String?) = stringToBitmap.map(string)
    fun map(response: ResponseBody?) = responseToBitmap.map(response)
}