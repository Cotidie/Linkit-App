package com.example.linkit.domain.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.linkit.LinkItApp

/** '디버그' 태그를 앞에 자동을 붙여준다. */
fun String.log() {
    Log.d("디버그", this)
}

/** Uri -> Bitmap */
fun Uri.toBitmap() : Bitmap {
    val appContext = LinkItApp.cxt()

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(appContext.contentResolver, this))
    } else {
        MediaStore.Images.Media.getBitmap(appContext.contentResolver, this)
    }
}

/** 빈 비트맵 */
val EMPTY_BITMAP = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)

/** 빈 Long: Long.MIN_VALUE */
val EMPTY_LONG = Long.MIN_VALUE