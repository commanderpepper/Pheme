package com.commanderpepper.pheme.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.commanderpepper.pheme.room.model.ArticleEntity

@Dao
interface ArticleDAO {

    /**
     * Get a list of Articles
     * @param category Category to filter which data is retrieved
     */
    @Query("SELECT * FROM articleentity WHERE category = :category")
    suspend fun getArticles(category: String): List<ArticleEntity>

    /**
     * Insert a list of articles
     * @param articles a list of Articles to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleEntity>): List<Long>

    /**
     * Get a single article using an ID
     * @param articleId get an article with this id
     */
    @Query("SELECT * FROM articleentity WHERE id = :articleId")
    suspend fun getArticle(articleId: Long): ArticleEntity

    /**
     * Delete EVERYTHING from the database
     */
    @Query("DELETE FROM articleentity")
    suspend fun deleteArticles()

    /**
     * Delete a set number of articles from database using category
     * @param category Category to delete from, should be taken from the Category Enum class
     * @param limit amount to delete from the database
     */
    @Query("DELETE FROM articleentity WHERE id IN (SELECT id FROM articleentity WHERE category = :category limit :limit)")
    suspend fun deleteArticlesFromCategory(category: String, limit: Int = 40)

    /**
     * Count the number of articles with the category
     * @param category the category used to filter which articles being counted
     */
    @Query("SELECT COUNT(*) FROM articleentity WHERE category = :category")
    suspend fun countArticles(category: String): Int
}