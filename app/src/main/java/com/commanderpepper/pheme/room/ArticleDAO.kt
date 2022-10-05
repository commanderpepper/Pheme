package com.commanderpepper.pheme.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.commanderpepper.pheme.room.model.ArticleEntity

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM articleentity WHERE category = :category")
    suspend fun getArticles(category: String): List<ArticleEntity>

    @Insert
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articleentity WHERE id = :articleId")
    suspend fun getArticle(articleId: Long): ArticleEntity

    @Query("DELETE FROM articleentity")
    suspend fun deleteArticles()
}