package com.commanderpepper.pheme.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.commanderpepper.pheme.room.model.ArticleEntity

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM articleentity WHERE category = :category")
    suspend fun getArticles(category: String): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articleentity WHERE id = :articleId")
    suspend fun getArticle(articleId: Long): ArticleEntity

    @Query("DELETE FROM articleentity")
    suspend fun deleteArticles()

    /**
     * @param category Category to delete from, should be taken from the Category Enum class
     * @param limit amount to delete from the database
     */
    @Query("DELETE FROM articleentity WHERE id IN (SELECT id FROM articleentity WHERE category = :category limit :limit)")
    suspend fun deleteArticlesFromCategory(category: String, limit: Int = 40)

    @Query("SELECT COUNT(*) FROM articleentity WHERE category = :category")
    suspend fun countArticles(category: String): Int
}