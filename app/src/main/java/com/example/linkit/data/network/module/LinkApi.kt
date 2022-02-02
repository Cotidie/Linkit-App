package com.example.linkit.data.network.module

import com.example.linkit.data.repository.UserRepository
import com.example.linkit.data.network.api.ILinkApi
import com.example.linkit.data.storage.LinkItPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 공유링크와 관련된 API를 제공하는 Retrofit 모듈
 */
@InstallIn(SingletonComponent::class)
@Module
class LinkApi {

//    @Singleton
//    @Provides
//    fun provideUserAPI() : ILinkApi {
//
//    }
}