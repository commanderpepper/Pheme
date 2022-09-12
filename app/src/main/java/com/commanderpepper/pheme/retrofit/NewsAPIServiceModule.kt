package com.commanderpepper.pheme.retrofit

import com.commanderpepper.pheme.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsAPIServiceModule {

    private const val baseURL = "https://newsapi.org/v2/"
    private const val apiKey = BuildConfig.NEWS_KEY
    private const val apiKeyName = "X-Api-Key"

    @Singleton
    @Provides
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader(apiKeyName, apiKey).build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        logger.redactHeader(apiKeyName)
        return OkHttpClient.Builder()
            .addInterceptor(logger)
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