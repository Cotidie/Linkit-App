package com.example.linkit.network.module

import com.example.linkit.data.repository.UserRepository
import com.example.linkit.network.api.ILinkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * 공유링크와 관련된 API를 제공하는 Retrofit 모듈
 */
@InstallIn(SingletonComponent::class)
@Module
class LinkApi {

    @Singleton
    @Provides
    fun provideUserRepository(
        api: ILinkApi
    ) = UserRepository(api)

//    @Singleton
//    @Provides
//    fun provideUserAPI() : ILinkApi {
//
//    }
}