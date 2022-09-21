package com.commanderpepper.pheme.repository.remote

import com.commanderpepper.pheme.CoroutinesScopesModule
import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.retrofit.NewsAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val newsAPIService: NewsAPIService,
    @CoroutinesScopesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NewsRemoteDataSource {

    override suspend fun retrieveCategoryArticles(category: String): List<Article> {
        val articleList = mutableListOf<Article>()
        withContext(ioDispatcher){
            val remoteArticles = newsAPIService.getCategoryArticles(category = category).articles
            articleList.addAll(remoteArticles)
        }
        return articleList
    }

    override suspend fun retrieveCountryArticles(category: String): List<Article> {
        val articleList = mutableListOf<Article>()
        withContext(ioDispatcher){
            val remoteArticles = newsAPIService.getCountryArticles().articles
            articleList.addAll(remoteArticles)
        }
        return articleList
    }
}