package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.CoroutinesScopesModule
import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsAPIService: NewsAPIService,
    @CoroutinesScopesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchNewsWithCategory(category: String): List<Article> =
        withContext(ioDispatcher) {
            newsAPIService.query(category).articles
        }
}