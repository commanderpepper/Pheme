package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.uistate.NewsItemUIState
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import javax.inject.Inject

class ConvertArticleEntityToNewsItemUIState @Inject constructor() {
    operator fun invoke(articleInBetween: ArticleInBetween): NewsItemUIState {
        return NewsItemUIState(
            publisher = articleInBetween.publisher,
            author = articleInBetween.author,
            title = articleInBetween.title,
            thumbnail = articleInBetween.thumbnail,
            content = articleInBetween.content
        )
    }
}