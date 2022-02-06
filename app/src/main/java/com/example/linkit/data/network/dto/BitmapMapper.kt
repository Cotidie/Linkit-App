package com.example.linkit.data.network.dto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.linkit.domain.interfaces.Mapper
import com.example.linkit.domain.model.EMPTY_BITMAP
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

/** 이미지 URL을 비트맵으로 변환한다. */
@Singleton
class BitmapMapper @Inject constructor() : Mapper<ResponseBody?, Bitmap> {
    override fun map(input: ResponseBody?): Bitmap {
        if (input == null) return EMPTY_BITMAP

        val rawByte = input.byteStream()
        return BitmapFactory.decodeStream(rawByte)
    }
}