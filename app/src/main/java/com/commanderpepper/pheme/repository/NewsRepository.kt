package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.data.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) {
    suspend fun fetchNewsWithCategory(topic: String): List<Article>{
        return newsRemoteDataSource.fetchNewsWithCategory(topic)
    }
}