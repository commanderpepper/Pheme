package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.Dispatchers
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsRemoteDataSourceTest {
    @Mock
    private lateinit var newsAPIService: NewsAPIService

    fun test_Get_Articles(){
        val newsRemoteDataSource = NewsRemoteDataSource(newsAPIService, Dispatchers.IO)

    }
}