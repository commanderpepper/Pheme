package com.commanderpepper.pheme.domain.usecase

import com.commanderpepper.pheme.data.retrofit.model.Article
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.data.room.model.ArticleEntity
import javax.inject.Inject

class CreateArticleEntityUseCase @Inject constructor() {
    operator fun invoke(category: Category, article: Article): ArticleEntity {
        return ArticleEntity(
            publisher = article.source.name,
            title = article.title,
            thumbnail = article.urlToImage ?: "",
            preview = article.description ?: "",
            content = article.content ?: article.description ?: "",
            category = category.category,
            author = article.author ?: "",
            publication = article.publishedAt ?: ""
        )
    }
}