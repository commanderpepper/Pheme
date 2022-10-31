package com.commanderpepper.pheme.data.retrofit

import com.commanderpepper.pheme.data.retrofit.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("top-headlines")
    suspend fun getCountryArticles(
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 50): Response

    @GET("top-headlines")
    suspend fun getCategoryArticles(
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = 50,
        @Query("country") country: String = "us"): Response
}