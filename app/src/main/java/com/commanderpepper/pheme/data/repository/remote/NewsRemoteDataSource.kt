package com.commanderpepper.pheme.data.repository.remote

import com.commanderpepper.pheme.data.retrofit.model.Article

interface NewsRemoteDataSource {

    /**
     * Retrieve articles based off of category passed
     * @param category category of articles to base retrieval off of
     */
    suspend fun retrieveCategoryArticles(category: String): List<Article>

    /**
     * Retrieve articles based off of country passed
     * @param country articles to base retrieval off of country passed
     */
    suspend fun retrieveCountryArticles(country: String): List<Article>
}