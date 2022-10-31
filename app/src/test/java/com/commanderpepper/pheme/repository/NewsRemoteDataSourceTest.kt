package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.data.retrofit.model.Article
import com.commanderpepper.pheme.data.retrofit.model.Response
import com.commanderpepper.pheme.data.retrofit.model.Source
import com.commanderpepper.pheme.data.repository.remote.NewsRemoteDataSourceImpl
import com.commanderpepper.pheme.data.retrofit.NewsAPIService
import kotlinx.coroutines.Dispatchers
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

    @Test
    fun test_Get_Articles() = runTest {
        Mockito.`when`(newsAPIService.getCountryArticles("Test")).thenReturn(mockResponse)

        val newsRemoteDataSource = NewsRemoteDataSourceImpl(newsAPIService, Dispatchers.IO)
        val response = newsRemoteDataSource.retrieveCountryArticles("Test")

        Assert.assertTrue(response == mockResponse.articles)
    }

    @Test
    fun getCategoryArticles() = runTest {
        Mockito.`when`(newsAPIService.getCategoryArticles("Test")).thenReturn(mockResponse)

        val newsRemoteDataSource = NewsRemoteDataSourceImpl(newsAPIService, Dispatchers.IO)
        val response = newsRemoteDataSource.retrieveCategoryArticles("Test")

        Assert.assertTrue(response == mockResponse.articles)
    }
}