package com.example.linkit.data.room

import android.graphics.Bitmap
import androidx.room.TypeConverter
import com.example.linkit.data.mapper.BinaryToBitmap
import com.example.linkit.data.mapper.BitmapToBinary
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class Converters @Inject constructor(
    private val bitmapToBinary: BitmapToBinary,
    private val binaryToBitmap: BinaryToBitmap,
){
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?) : ByteArray? = bitmapToBinary.map(bitmap)

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?) : Bitmap? = binaryToBitmap.map(byteArray)
}