package com.commanderpepper.pheme.repository

import android.util.Log
import com.commanderpepper.pheme.repository.local.Category
import com.commanderpepper.pheme.repository.local.NewsLocalDataSource
import com.commanderpepper.pheme.repository.remote.NewsRemoteDataSource
import com.commanderpepper.pheme.usecase.ConvertArticleEntityToArticleInBetweenUseCase
import com.commanderpepper.pheme.usecase.CreateArticleEntityUseCase
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val createArticleEntityUseCase: CreateArticleEntityUseCase,
    private val convertArticleEntityToArticleInBetweenUseCase: ConvertArticleEntityToArticleInBetweenUseCase
) : NewsRepository {

    override fun fetchNewsWithCategory(category: Category): Flow<Status<out List<ArticleInBetween>>> {
        return flow {
            emit(Status.InProgress)

            try {
                val remoteArticles = newsRemoteDataSource.retrieveCategoryArticles(category.category)
                newsLocalDataSource.insertArticles(remoteArticles.map {
                    createArticleEntityUseCase(
                        category,
                        it
                    )
                })
            }catch (exception: Exception){
                Log.e(NewsRepository::class.toString(), exception.toString())
            }

            val localArticles = newsLocalDataSource.getArticles(category)
            if (localArticles.isNotEmpty()) {
                emit(Status.Success(localArticles.map {
                    convertArticleEntityToArticleInBetweenUseCase(
                        it
                    )
                }.take(50)))
            } else {
                emit(Status.Failure("Something is bad"))
            }
        }.catch {
            Log.e(NewsRepository::class.toString(), it.toString())
            emit(Status.Failure("Something is bad"))
        }
    }

    override fun fetchNewsWithCountry(category: Category): Flow<Status<out List<ArticleInBetween>>> {
        return flow {
            emit(Status.InProgress)

            try {
                val remoteArticles = newsRemoteDataSource.retrieveCountryArticles(category.category)
                newsLocalDataSource.insertArticles(remoteArticles.map {
                    createArticleEntityUseCase(
                        category,
                        it
                    )
                })
            }catch (exception: Exception){
                Log.e(NewsRepository::class.toString(), exception.toString())
            }

            val localArticles = newsLocalDataSource.getArticles(category)
            if (localArticles.isNotEmpty()) {
                emit(Status.Success(localArticles.map {
                    convertArticleEntityToArticleInBetweenUseCase(
                        it
                    )
                }.take(50)))
            } else {
                emit(Status.Failure("Something is bad"))
            }

        }.catch {
            Log.e(NewsRepository::class.toString(), it.toString())
            emit(Status.Failure("Something is bad"))
        }
    }

    override fun fetchSingleArticle(articleId: Long): Flow<Status<out ArticleInBetween>> {
        return flow {
            emit(Status.InProgress)

            val articleEntity = newsLocalDataSource.getArticle(articleId)
            if (articleEntity != null) {
                emit(Status.Success(convertArticleEntityToArticleInBetweenUseCase(articleEntity)))
            } else {
                emit(Status.Failure("Article not found"))
            }
        }.catch {
            Log.e(NewsRepository::class.toString(), "Unable to get single article")
            Log.e(NewsRepository::class.toString(), it.toString())
            emit(Status.Failure("Something went wrong"))
        }
    }

    override suspend fun deleteArticles() {
        newsLocalDataSource.deleteAllArticles()
    }
}