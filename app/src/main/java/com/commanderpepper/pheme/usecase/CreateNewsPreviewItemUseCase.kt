package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import javax.inject.Inject

class CreateNewsPreviewItemUseCase @Inject constructor() {
    operator fun invoke(article: Article): NewsPreviewItemUIState{
        return NewsPreviewItemUIState(
            publisher = article.source.name,
            author = article.author ?: "",
            title = article.title,
            preview = article.description,
            thumbnail = article.urlToImage ?: ""
        )
    }
}