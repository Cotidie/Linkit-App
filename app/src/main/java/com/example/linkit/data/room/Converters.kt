package com.example.linkit.data.room

import android.graphics.Bitmap
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.linkit.data.mapper.BitmapMapper
import com.example.linkit.data.mapper.DateMapper
import java.util.*
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(
    private val bitmapMapper: BitmapMapper,
    private val dateMapper: DateMapper
){
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? = bitmapMapper.map(bitmap)
    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? = bitmapMapper.map(byteArray)
}