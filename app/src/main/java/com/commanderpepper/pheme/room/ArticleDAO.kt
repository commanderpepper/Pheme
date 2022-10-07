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

    @Query("DELETE FROM articleentity WHERE id IN (SELECT id FROM articleentity WHERE category = :category limit 40)")
    suspend fun deleteFortyArticles(category: String)

    @Query("SELECT COUNT(*) FROM articleentity WHERE category = :category")
    suspend fun countArticles(category: String): Int
}