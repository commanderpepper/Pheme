package com.commanderpepper.pheme.data.repository.local

import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.data.room.model.ArticleEntity

interface NewsLocalDataSource  {

    /**
     * Gets articles according to the Category passed
     * @param category Value from Category enum class
     * @return a list of ArticleEntity
     */
    suspend fun getArticles(category: Category): List<ArticleEntity>

    /**
     * Insert a list of ArticleEntity
     * @param articles List of ArticleEntity
     * @return List of ArticleEntity objects inserted from this function
     */
    suspend fun insertArticles(articles: List<ArticleEntity>): List<ArticleEntity>

    /**
     * Get an ArticleEntity using an ID
     * @param id ID of ArticleEntity to retrieve
     * @return ArticleEntity
     */
    suspend fun getArticle(id: Long): ArticleEntity

    /**
     * Delete articles from the local database
     */
    suspend fun deleteArticles()

    /**
     * Delete a set number of articles from the local database from a category
     * @param category Value from Category enum class
     * @param amountToDelete number of articles to delete
     */
    suspend fun deleteArticles(category: Category, amountToDelete: Int)
}