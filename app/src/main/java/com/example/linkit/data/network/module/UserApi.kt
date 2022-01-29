package com.example.linkit.network.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * [IUserApi]에 명세된 API를 제공하기 위한 Hilt 의존성 주입 모듈
 */
@InstallIn(SingletonComponent::class)
@Module
class UserApi {
}