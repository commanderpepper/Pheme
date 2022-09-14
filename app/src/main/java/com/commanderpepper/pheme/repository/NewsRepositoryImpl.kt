package com.commanderpepper.pheme.repository

import android.util.Log
import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.repository.local.Category
import com.commanderpepper.pheme.repository.local.NewsLocalDataSource
import com.commanderpepper.pheme.repository.remote.NewsRemoteDataSource
import com.commanderpepper.pheme.room.model.ArticleEntity
import com.commanderpepper.pheme.usecase.ConvertArticleEntityToArticleInBetweenUseCase
import com.commanderpepper.pheme.usecase.CreateArticleEntityUseCase
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val convertArticleEntityToArticleInBetweenUseCase: ConvertArticleEntityToArticleInBetweenUseCase): NewsRepository {

    override fun fetchNewsWithCategory(category: Category): Flow<Status<out List<ArticleInBetween>>> {
        return flow {
            emit(Status.InProgress)

            val remoteArticles = newsRemoteDataSource.retrieveCategoryArticles(category.category)
            newsLocalDataSource.insertArticles(category, remoteArticles)

            val  localArticles = newsLocalDataSource.getArticles(category)
            emit(Status.Success(localArticles.first().map { convertArticleEntityToArticleInBetweenUseCase(it) }))
        }.catch {
            Log.e("Repository", "Something went wrong")
            emit(Status.Failure("Something is bad"))
        }
    }

    override fun fetchNewsWithCountry(category: Category): Flow<Status<out List<ArticleInBetween>>> {
        return flow {
            emit(Status.InProgress)

            val remoteArticles =  newsRemoteDataSource.retrieveCountryArticles(category.category)
            newsLocalDataSource.insertArticles(category, remoteArticles)

            val  localArticles = newsLocalDataSource.getArticles(category)
            emit(Status.Success(localArticles.first().map { convertArticleEntityToArticleInBetweenUseCase(it) }))
        }.catch {
            Log.e("Repository", "Something went wrong")
            emit(Status.Failure("Something is bad"))
        }
    }
}