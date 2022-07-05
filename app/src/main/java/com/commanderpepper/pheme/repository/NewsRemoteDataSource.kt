package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.CoroutinesScopesModule
import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
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

    fun getArticles(category: String) = flow {
        emit(ResultOf.Loading)
        try {
            val articles = withContext(ioDispatcher) { newsAPIService.query(category).articles }
            if(articles.isNotEmpty()){
                emit(ResultOf.Success(articles))
            }
            else {
                emit(ResultOf.Error("No articles found"))
            }
        }
        catch(exception: java.lang.Exception) {
            emit(ResultOf.Error("Something went wrong"))
        }
    }
}