package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.repository.local.Category
import com.commanderpepper.pheme.room.model.ArticleEntity
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
            publication = article.publishedAt
        )
    }
}