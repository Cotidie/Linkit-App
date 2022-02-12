package com.example.linkit.data.mapper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.linkit.domain.interfaces.Mapper
import com.example.linkit.domain.model.EMPTY_BITMAP
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/** 이미지 URL을 비트맵으로 변환한다. */
@Singleton
class ResponseToBitmap @Inject constructor() : Mapper<ResponseBody?, Bitmap> {
    override fun map(input: ResponseBody?): Bitmap {
        if (input == null) return EMPTY_BITMAP

        val rawByte = input.byteStream()
        return BitmapFactory.decodeStream(rawByte)
    }
}

@Singleton
class StringToBitmap @Inject constructor(): Mapper<String, Bitmap> {
    override fun map(input: String): Bitmap {
        TODO("Not yet implemented")
    }
}

@Singleton
class BinaryToBitmap @Inject constructor(): Mapper<ByteArray?, Bitmap?> {
    override fun map(input: ByteArray?): Bitmap? {
        if (input == null) return null

        return BitmapFactory.decodeByteArray(input, 0, input.size)
    }
}

@Singleton
class BitmapToBinary @Inject constructor(): Mapper<Bitmap?, ByteArray?> {
    override fun map(input: Bitmap?): ByteArray? {
        if (input == null) return null

        val outputStream = ByteArrayOutputStream()
        input.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}