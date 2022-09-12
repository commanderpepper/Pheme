package com.commanderpepper.pheme.repository.remote

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.repository.ResultOf
import kotlinx.coroutines.flow.Flow

interface NewsRemoteDataSource {
    fun getArticles(category: String): Flow<ResultOf<out List<Article>>>
}