package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.repository.local.Category
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun fetchArticles(category: Category): Flow<Status<out List<ArticleInBetween>>>

    fun fetchSingleArticle(articleId: Long): Flow<Status<out ArticleInBetween>>

    suspend fun deleteArticles()
}