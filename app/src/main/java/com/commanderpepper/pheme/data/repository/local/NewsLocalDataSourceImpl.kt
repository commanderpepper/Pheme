package com.commanderpepper.pheme.data.repository.local

import com.commanderpepper.pheme.CoroutinesScopesModule
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.data.room.ArticleDAO
import com.commanderpepper.pheme.data.room.model.ArticleEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(
    private val articleDAO: ArticleDAO,
    @CoroutinesScopesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
): NewsLocalDataSource {

    override suspend fun getArticles(category: Category): List<ArticleEntity> = withContext(ioDispatcher){
        articleDAO.getArticles(category.category)
    }

    override suspend fun insertArticles(articles: List<ArticleEntity>): List<ArticleEntity> {
        return withContext(ioDispatcher) {
            val ids = articleDAO.insertArticles(articles)
            ids.map {
                getArticle(it)
            }
        }
    }

    override suspend fun getArticle(id: Long) = withContext(ioDispatcher){
        articleDAO.getArticle(id)
    }

    override suspend fun deleteArticles() {
        withContext(ioDispatcher){
            Category.values().forEach { category ->
                val count = articleDAO.countArticles(category.category)
                if(count > 60){
                    articleDAO.deleteArticlesFromCategory(category.category)
                }
            }
        }
    }

    override suspend fun deleteArticles(category: Category, amountToDelete: Int) {
        withContext(ioDispatcher){
            val count = articleDAO.countArticles(category.category)
            if(count > 60){
                articleDAO.deleteArticlesFromCategory(category.category, amountToDelete)
            }
        }
    }
}