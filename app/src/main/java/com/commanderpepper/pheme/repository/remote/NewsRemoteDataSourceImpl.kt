package com.commanderpepper.pheme.repository.remote

import android.util.Log
import com.commanderpepper.pheme.CoroutinesScopesModule
import com.commanderpepper.pheme.repository.ResultOf
import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsAPIService: NewsAPIService,
    @CoroutinesScopesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NewsRemoteDataSource {

    override fun getArticles(category: String) = flow {
        emit(ResultOf.Loading)
//        val articles = withContext(ioDispatcher) { newsAPIService.query(category).articles }
        val articles = withContext(ioDispatcher) { newsAPIService.getAmericanArticles().articles }

        if (articles.isNotEmpty()) {
            emit(ResultOf.Success(articles))
        } else {
            emit(ResultOf.Error("No articles found"))
        }
    }.catch { exception ->
        Log.e("News Remote Data Source", exception.message ?: "Unknown message")
        emit(ResultOf.Error("Something went wrong"))
    }
}