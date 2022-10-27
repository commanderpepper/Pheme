package com.commanderpepper.pheme.repository

import android.util.Log
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.data.Category
import com.commanderpepper.pheme.repository.local.NewsLocalDataSource
import com.commanderpepper.pheme.repository.remote.NewsRemoteDataSource
import com.commanderpepper.pheme.usecase.ConvertArticleEntityToArticleInBetweenUseCase
import com.commanderpepper.pheme.usecase.CreateArticleEntityUseCase
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import com.commanderpepper.pheme.util.StringProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val createArticleEntityUseCase: CreateArticleEntityUseCase,
    private val convertArticleEntityToArticleInBetweenUseCase: ConvertArticleEntityToArticleInBetweenUseCase,
    private val stringProvider: StringProvider
) : NewsRepository {

    private suspend fun getArticles(category: Category): List<Article> {
        return when(category){
            Category.NEWS -> newsRemoteDataSource.retrieveCountryArticles(category.category)
            else -> newsRemoteDataSource.retrieveCategoryArticles(category.category)
        }
    }

    override fun fetchArticles(category: Category): Flow<Status<out List<ArticleInBetween>>> {
        return flow {
            emit(Status.InProgress)

            // Try to retrieve articles from the local source
            val articlesFromDatabase = newsLocalDataSource.getArticles(category)
            if(articlesFromDatabase.isNotEmpty()){
                emit(Status.Success(articlesFromDatabase.map {
                    convertArticleEntityToArticleInBetweenUseCase(
                        it
                    )
                }.take(50)))
            }

            try {
                val remoteArticles = getArticles(category)
                val insertedArticles = newsLocalDataSource.insertArticles(remoteArticles.map {
                    createArticleEntityUseCase(
                        category,
                        it
                    )
                })

                if (insertedArticles.isNotEmpty()) {
                    emit(Status.Success(insertedArticles.map {
                        convertArticleEntityToArticleInBetweenUseCase(
                            it
                        )
                    }.take(50)))
                } else {
                    emit(Status.Failure(stringProvider.getString(R.string.news_repository_error_message)))
                }
            }catch (exception: Exception){
                Log.e(NewsRepository::class.toString(), exception.toString())
                if(articlesFromDatabase.isEmpty()){
                    emit(Status.Failure(stringProvider.getString(R.string.error_message)))
                }
            }
        }.catch {
            Log.e(NewsRepository::class.toString(), it.toString())
            emit(Status.Failure(stringProvider.getString(R.string.news_repository_error_message)))
        }
    }

    override fun fetchSingleArticle(articleId: Long): Flow<Status<out ArticleInBetween>> {
        return flow {
            emit(Status.InProgress)

            val articleEntity = newsLocalDataSource.getArticle(articleId)
            if (articleEntity != null) {
                emit(Status.Success(convertArticleEntityToArticleInBetweenUseCase(articleEntity)))
            } else {
                emit(Status.Failure(stringProvider.getString(R.string.news_repository_article_not_found)))
            }
        }.catch {
            Log.e(NewsRepository::class.toString(), stringProvider.getString(R.string.news_repository_unable_to_fetch_article))
            Log.e(NewsRepository::class.toString(), it.toString())
            emit(Status.Failure(stringProvider.getString(R.string.error_message)))
        }
    }

    override suspend fun deleteArticles() {
        newsLocalDataSource.deleteArticles()
    }
}