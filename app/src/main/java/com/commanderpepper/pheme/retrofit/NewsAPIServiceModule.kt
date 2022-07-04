package com.commanderpepper.pheme.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsAPIServiceModule {

    private const val baseURL = "https://newsapi.org/v2/"

//    fun createNewsAPIService(): NewsAPIService {
//        val builder = OkHttpClient.Builder()
//
//        builder.addInterceptor { chain ->
//            val request: Request =
//                chain.request().newBuilder()
//                    .addHeader("X-Api-Key", apiKey).build()
//            chain.proceed(request)
//        }
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(baseURL)
//            .client(builder.build())
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//
//        return retrofit.create(NewsAPIService::class.java)
//    }

    @Singleton
    @Provides
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("X-Api-Key", apiKey).build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseURL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideNewsAPIService(retrofit: Retrofit): NewsAPIService = retrofit.create(NewsAPIService::class.java)
}