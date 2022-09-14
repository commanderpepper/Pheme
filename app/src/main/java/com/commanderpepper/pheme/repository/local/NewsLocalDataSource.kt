package com.commanderpepper.pheme.repository.local

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.room.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource  {

    suspend fun getArticles(category: Category): List<ArticleEntity>

    suspend fun insertArticles(category: Category, articles: List<Article>)

    suspend fun getArticle(id: Long): ArticleEntity

    suspend fun deleteAllArticles()
}

enum class Category(val category: String){
    NEWS("news"),
    SPORTS("sports"),
    TECH("technology"),
    ENTERTAINMENT("entertainment")
}