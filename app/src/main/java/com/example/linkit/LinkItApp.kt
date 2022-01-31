package com.example.linkit

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LinkItApp : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: Application
        fun cxt() : Context = instance.applicationContext
    }
}