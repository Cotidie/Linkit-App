package com.apptive.linkit.data.network

import com.apptive.linkit.data.network.api.IDeveloperApi
import com.apptive.linkit.data.network.api.ILinkApi
import com.apptive.linkit.data.network.api.IUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    private val BASE_URL = "https://link-manage-apptive.herokuapp.com/"

    @Singleton
    @Provides
    @Named("AuthInterceptor")
    /** 요청 헤더에 JWT 토큰(로그인 인증 용도)을 붙여 보낸다. */
    fun providesAuthInterceptor() : Interceptor {
        return Interceptor {
            // 기존 요청에 헤더를 붙인다.
            val newRequest = it.request()
                .newBuilder()
                .addHeader("Authorization", "aaaaa")
                .build()
            // 요청을 보낸다.
            it.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    @Named("LogginInterceptor")
    /** 요청과 응답마다 Body 부분을 디버그 로깅한다. */
    fun providesLogginInterceptor() : Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    /** Retrofit에서 이용할 OkHttpClient 생성 */
    fun providesOkHttpClient(
        @Named("AuthInterceptor")
        authInterceptor: Interceptor,
        @Named("LogginInterceptor")
        loggingInterceptor: Interceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    /** 결과적으로 활용할 Retrofit 인스턴스 */
    fun providesRetrofitClient(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    /** 이 IUserApi 구현체는 작성한 Api에 대한 요청, 응답을 수행한다. */
    fun providesUserApi(retrofit: Retrofit) : IUserApi {
        return retrofit.create(IUserApi::class.java)
    }

    @Singleton
    @Provides
    /** IDeveloperAPI를 구현한다. */
    fun providesDeveloperApi(retrofit: Retrofit) : IDeveloperApi {
        return retrofit.create(IDeveloperApi::class.java)
    }

    @Singleton
    @Provides
    /** ILinkAPI를 구현한다 */
    fun providesLinkApi(retrofit: Retrofit) : ILinkApi {
        return retrofit.create(ILinkApi::class.java)
    }
}