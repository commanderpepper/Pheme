package com.commanderpepper.pheme.domain.usecase

import com.commanderpepper.pheme.ui.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.domain.usecase.model.ArticleInBetween
import javax.inject.Inject

class CreateNewsPreviewItemUseCase @Inject constructor() {
    operator fun invoke(articleInBetween: ArticleInBetween): NewsPreviewItemUIState {
        return NewsPreviewItemUIState(
            publisher = articleInBetween.publisher,
            author = articleInBetween.author,
            title = articleInBetween.title,
            thumbnail = articleInBetween.thumbnail,
            id = articleInBetween.id
        )
    }
}