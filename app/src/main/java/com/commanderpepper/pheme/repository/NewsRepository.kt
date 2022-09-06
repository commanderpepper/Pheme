package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.data.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun fetchNewsWithCategory(category: String): Flow<Status<out List<Article>>>
}