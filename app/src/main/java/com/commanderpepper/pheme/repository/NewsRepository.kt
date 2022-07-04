package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.data.Article
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) {
    suspend fun fetchNewsWithCategory(topic: String): List<Article>{
        return newsRemoteDataSource.fetchNewsWithCategory(topic)
    }
}