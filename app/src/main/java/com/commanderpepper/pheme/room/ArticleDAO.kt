package com.commanderpepper.pheme.room.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM articleentity WHERE category = :category")
    fun getArticles(category: String): Flow<List<ArticleEntity>>

    @Insert
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articleentity")
    suspend fun deleteArticles()
}