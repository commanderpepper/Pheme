package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.data.Source
import com.commanderpepper.pheme.room.model.ArticleEntity
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import javax.inject.Inject

class ConvertArticleEntityToArticleInBetweenUseCase @Inject constructor() {
    operator fun invoke(articleEntity: ArticleEntity): ArticleInBetween {
        return ArticleInBetween(
            id = articleEntity.id,
            publisher = articleEntity.publisher,
            author = articleEntity.author,
            title = articleEntity.title,
            preview = articleEntity.preview,
            thumbnail = articleEntity.thumbnail,
            content = articleEntity.content
        )
    }
}