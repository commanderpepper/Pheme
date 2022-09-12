package com.commanderpepper.pheme.room.model

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM articleentity WHERE category = :category")
    fun getUSArticles(category: String = "us"): Flow<List<ArticleEntity>>
}