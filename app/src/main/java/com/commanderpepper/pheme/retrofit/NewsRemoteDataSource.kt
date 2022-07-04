package com.commanderpepper.pheme.retrofit

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.repository.ResultOf
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.util.concurrent.Flow

class NewsRemoteDataSource(val remoteSource: NewsAPIService) {

    fun getArticles(topic: String) = flow {
       emit(ResultOf.Loading)
       try {
           val articles = remoteSource.query(topic).articles
           if(articles.isNotEmpty()){
               emit(ResultOf.Success(articles))
           }
           else {
               emit(ResultOf.Error("No articles found"))
           }
       }
       catch(exception: Exception) {
           emit(ResultOf.Error("Something went wrong"))
       }
   }
}