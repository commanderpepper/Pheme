package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.CoroutinesScopesModule
import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsAPIService: NewsAPIService,
    @CoroutinesScopesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
): NewsRemoteDataSource {

    override fun getArticles(category: String) = flow {
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
        catch(exception: Exception) {
            emit(ResultOf.Error("Something went wrong"))
        }
    }
}