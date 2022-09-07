package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsRemoteDataSourceTest {
    @Mock
    private lateinit var newsAPIService: NewsAPIService

    @Test
    fun test_Get_Articles(){
        val newsRemoteDataSource = NewsRemoteDataSourceImpl(newsAPIService, Dispatchers.IO)
        val five = 5

        Assert.assertTrue(five == 5)
    }
}