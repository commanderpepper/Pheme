package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.data.Response
import com.commanderpepper.pheme.data.Source
import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsRemoteDataSourceTest {

    private val mockResponse = Response(
        "Okay",
        10,
        listOf(
            Article(
                Source("NYT", "New York Times"),
                "John Smith",
                "This is a test!",
                "This is in fact a test.",
                "test.test.test",
                "test.test.test.test.png",
                "today",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        )
    )

    @Mock
    private lateinit var newsAPIService: NewsAPIService

    @Before
    fun setUpMocks(){
        newsAPIService = mock(NewsAPIService::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_Get_Articles() = runTest {
        Mockito.`when`(newsAPIService.query("Test")).thenReturn(mockResponse)

        val newsRemoteDataSource = NewsRemoteDataSourceImpl(newsAPIService, Dispatchers.IO)
        val response = newsRemoteDataSource.getArticles("Test")

        response.collect {
            if(it is ResultOf.Success){
                Assert.assertTrue(it.data.first() == mockResponse.articles.first())
            }
        }
    }
}