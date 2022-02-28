package com.apptive.linkit.data.mapper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.apptive.linkit.domain.interfaces.Mapper
import com.apptive.linkit.domain.model.EMPTY_BITMAP
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapMapper @Inject constructor() {
    private val responseToBitmap = ResponseToBitmap()
    private val stringToBitmap = StringToBitmap()
    private val binaryToBitmap = BinaryToBitmap()
    private val bitmapToBinary = BitmapToBinary()

    fun map(input: ResponseBody?): Bitmap = responseToBitmap.map(input)
    fun map(input: String?): Bitmap = stringToBitmap.map(input)
    fun map(input: ByteArray?): Bitmap? = binaryToBitmap.map(input)
    fun map(input: Bitmap?): ByteArray? = bitmapToBinary.map(input)
}

/** 이미지 URL을 비트맵으로 변환한다. */
private class ResponseToBitmap: Mapper<ResponseBody?, Bitmap> {
    override fun map(input: ResponseBody?): Bitmap {
        if (input == null) return EMPTY_BITMAP

        val rawByte = input.byteStream()
        return BitmapFactory.decodeStream(rawByte)
    }
}

private class StringToBitmap: Mapper<String?, Bitmap> {
    private val binaryToBitmap = BinaryToBitmap()

    override fun map(input: String?): Bitmap {
        if (input == null) return EMPTY_BITMAP

        return try {
//            val encodeByte: ByteArray = Base64.decode(input, Base64.DEFAULT)
//            binaryToBitmap.map(encodeByte)!!
            val url2 = URL(input)
            val connection = url2.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.message
            EMPTY_BITMAP
        }
    }
}

private class BinaryToBitmap: Mapper<ByteArray?, Bitmap?> {
    override fun map(input: ByteArray?): Bitmap? {
        if (input == null) return null

        return BitmapFactory.decodeByteArray(input, 0, input.size)
    }
}

private class BitmapToBinary: Mapper<Bitmap?, ByteArray?> {
    override fun map(input: Bitmap?): ByteArray? {
        if (input == null) return null

        val outputStream = ByteArrayOutputStream()
        input.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}