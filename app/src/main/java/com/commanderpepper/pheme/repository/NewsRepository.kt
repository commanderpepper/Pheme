package com.commanderpepper.pheme.repository

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) {

    fun fetchNewsWithCategory(category: String) = newsRemoteDataSource.getArticles(category).map { resultOf ->
        when(resultOf){
            is ResultOf.Loading -> Status.InProgress
            is ResultOf.Success -> Status.Success(resultOf.data)
            is ResultOf.Error -> Status.Failure("Something went wrong")
        }
    }

}