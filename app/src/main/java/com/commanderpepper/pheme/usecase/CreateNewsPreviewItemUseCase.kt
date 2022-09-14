package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import javax.inject.Inject

class CreateNewsPreviewItemUseCase @Inject constructor() {
    operator fun invoke(articleInBetween: ArticleInBetween): NewsPreviewItemUIState{
        return NewsPreviewItemUIState(
            publisher = articleInBetween.publisher,
            author = articleInBetween.author,
            title = articleInBetween.title,
            preview = articleInBetween.preview,
            thumbnail = articleInBetween.thumbnail,
            id = articleInBetween.id
        )
    }
}