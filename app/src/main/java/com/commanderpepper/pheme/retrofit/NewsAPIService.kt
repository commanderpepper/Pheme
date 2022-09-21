package com.commanderpepper.pheme.retrofit

import com.commanderpepper.pheme.data.Response
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsAPIService {

    @GET("everything")
    suspend fun query(@Query("q") query: String) : Response

    @GET("top-headlines")
    suspend fun getCountryArticles(
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 50): Response

    @GET("top-headlines")
    suspend fun getCategoryArticles(
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = 50): Response
}