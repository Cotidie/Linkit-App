package com.example.linkit.data.network.dto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.linkit.domain.interfaces.Mapper
import okhttp3.Response
import okhttp3.ResponseBody

/** 이미지 URL을 비트맵으로 변환한다. */
class BitmapMapper : Mapper<ResponseBody, Bitmap> {
    override fun map(input: ResponseBody): Bitmap {
        val rawByte = input.byteStream()
        return BitmapFactory.decodeStream(rawByte)
    }
}