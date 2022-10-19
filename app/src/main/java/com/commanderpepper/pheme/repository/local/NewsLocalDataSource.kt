package com.commanderpepper.pheme.repository.local

import com.commanderpepper.pheme.room.model.ArticleEntity

interface NewsLocalDataSource  {

    suspend fun getArticles(category: Category): List<ArticleEntity>

    suspend fun insertArticles(articles: List<ArticleEntity>)

    suspend fun getArticle(id: Long): ArticleEntity

    suspend fun deleteArticles()

    suspend fun deleteArticles(category: String, amountToDelete: Int)
}

enum class Category(val category: String){
    NEWS("news"),
    SPORTS("sports"),
    TECH("technology"),
    ENTERTAINMENT("entertainment")
}