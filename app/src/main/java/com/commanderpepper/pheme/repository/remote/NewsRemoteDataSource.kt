package com.commanderpepper.pheme.repository.remote

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.repository.ResultOf
import com.commanderpepper.pheme.repository.local.Category
import kotlinx.coroutines.flow.Flow

interface NewsRemoteDataSource {
    suspend fun retrieveCategoryArticles(category: String): List<Article>

    suspend fun retrieveCountryArticles(category: String): List<Article>
}