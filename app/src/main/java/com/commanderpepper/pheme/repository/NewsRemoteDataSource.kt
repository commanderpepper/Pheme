package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.data.Article
import kotlinx.coroutines.flow.Flow

interface NewsRemoteDataSource {
    fun getArticles(category: String): Flow<ResultOf<out List<Article>>>
}