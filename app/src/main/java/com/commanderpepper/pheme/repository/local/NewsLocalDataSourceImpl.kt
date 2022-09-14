package com.commanderpepper.pheme.repository.local

import com.commanderpepper.pheme.CoroutinesScopesModule
import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.room.model.ArticleDAO
import com.commanderpepper.pheme.room.model.ArticleEntity
import com.commanderpepper.pheme.uistate.NewsItemUIState
import com.commanderpepper.pheme.usecase.CreateArticleEntityUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(
    private val articleDAO: ArticleDAO,
    private val createArticleEntityUseCase: CreateArticleEntityUseCase,
    @CoroutinesScopesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
): NewsLocalDataSource {

    override fun getArticles(category: Category): Flow<List<ArticleEntity>> {
        return articleDAO.getArticles(category.category)
    }

    override suspend fun insertArticles(category: Category, articles: List<Article>) {
        withContext(ioDispatcher){
            articleDAO.insertArticles(articles.map {
                createArticleEntityUseCase(category, it)
            })
        }
    }
}