package com.commanderpepper.pheme.repository

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.repository.remote.NewsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource): NewsRepository {

    override fun fetchNewsWithCategory(category: String): Flow<Status<out List<Article>>> = newsRemoteDataSource.getArticles(category).map { resultOf ->
        when(resultOf){
            is ResultOf.Loading -> Status.InProgress
            is ResultOf.Success -> Status.Success(resultOf.data)
            is ResultOf.Error -> Status.Failure("Something went wrong")
        }
    }
}