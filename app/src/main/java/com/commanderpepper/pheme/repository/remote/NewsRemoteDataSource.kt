package com.commanderpepper.pheme.repository.remote

import com.commanderpepper.pheme.data.Article

interface NewsRemoteDataSource {
    suspend fun retrieveCategoryArticles(category: String): List<Article>

    suspend fun retrieveCountryArticles(category: String): List<Article>
}