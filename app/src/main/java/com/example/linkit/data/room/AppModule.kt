package com.example.linkit.data.room

import android.content.Context
import androidx.room.Room
import com.example.linkit.data.room.dao.LinkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesLinkDao(linkitDatabase: LinkItDatabase) : LinkDao
        = linkitDatabase.linkDao()

    @Singleton
    @Provides
    fun provideLinkItDatabase(@ApplicationContext context: Context) : LinkItDatabase
        = Room.databaseBuilder(
        context,
        LinkItDatabase::class.java,
        "linkit_db")
        // 스키마를 업데이트하면 기존 데이터를 비운다.
        .fallbackToDestructiveMigration()
        .build()
}