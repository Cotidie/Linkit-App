package com.apptive.linkit.domain.mapper

import com.apptive.linkit.data.mapper.BitmapMapper
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapMappers @Inject constructor(
    private val bitmapMapper: BitmapMapper
) {
    fun map(string: String?) = bitmapMapper.map(string)
    fun map(response: ResponseBody?) = bitmapMapper.map(response)
}