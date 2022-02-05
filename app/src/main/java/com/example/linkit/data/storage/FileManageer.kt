package com.example.linkit.data.storage

import android.graphics.Bitmap
import android.net.Uri
import com.example.linkit.LinkItApp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/** 파일 저장, 불러오기, 이동 등의 기능을 수행한다. */
class FileManageer @Inject constructor() {
    private val context = LinkItApp.cxt()

    /**
     * 전달된 [Bitmap]을 해당하는 경로에 저장한다.
     * @param bitmap: 변환할 비트맵
     * @param path: 저장할 경로
     * @return 저장한 파일의 Uri
     */
    fun saveBitmap(bitmap: Bitmap, path: String) : Uri {
        return Uri.EMPTY
    }
}