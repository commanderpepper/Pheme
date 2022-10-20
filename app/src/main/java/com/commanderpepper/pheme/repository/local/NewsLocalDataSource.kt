package com.commanderpepper.pheme.repository.local

import com.commanderpepper.pheme.room.model.ArticleEntity

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
     */
    suspend fun insertArticles(articles: List<ArticleEntity>)

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

enum class Category(val category: String){
    NEWS("news"),
    SPORTS("sports"),
    TECH("technology"),
    ENTERTAINMENT("entertainment")
}