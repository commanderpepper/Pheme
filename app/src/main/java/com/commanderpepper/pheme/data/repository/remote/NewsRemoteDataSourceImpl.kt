package com.commanderpepper.pheme.data.repository.remote

import com.commanderpepper.pheme.CoroutinesScopesModule
import com.commanderpepper.pheme.data.retrofit.model.Article
import com.commanderpepper.pheme.data.retrofit.NewsAPIService
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

    override suspend fun retrieveCountryArticles(country: String): List<Article> {
        val articleList = mutableListOf<Article>()
        withContext(ioDispatcher){
            val remoteArticles = newsAPIService.getCountryArticles().articles
            articleList.addAll(remoteArticles)
        }
        return articleList
    }
}