package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.repository.local.Category
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    /**
     * Fetch a List of Articles wrapped in a Status sealed class
     * @param category Condition to determine which articles are returned
     * @return A List of Articles wrapped in a Status sealed class
     */
    fun fetchArticles(category: Category): Flow<Status<out List<ArticleInBetween>>>

    /**
     * Fetch an Articles wrapped in Status
     * @param articleId Article ID to determine which article is returned
     * @return An Article wrapped in a Status sealed class
     */
    fun fetchSingleArticle(articleId: Long): Flow<Status<out ArticleInBetween>>

    /**
     * Delete articles
     */
    suspend fun deleteArticles()
}