package com.commanderpepper.pheme

import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class NewsAPIServiceTest {
    private val newsAPIService = NewsAPIService.instance

    @Test
    fun test_service() = runBlocking {
        val data = newsAPIService.query("America")
        Assert.assertTrue(data.articles.isNotEmpty())
    }
}