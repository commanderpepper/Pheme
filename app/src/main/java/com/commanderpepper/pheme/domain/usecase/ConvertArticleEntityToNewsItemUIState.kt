package com.commanderpepper.pheme.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.commanderpepper.pheme.ui.uistate.NewsItemUIState
import com.commanderpepper.pheme.domain.usecase.model.ArticleInBetween
import javax.inject.Inject

class ConvertArticleEntityToNewsItemUIState @Inject constructor(val convertISODateToStringUseCase: ConvertISODateToStringUseCase) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(articleInBetween: ArticleInBetween): NewsItemUIState {
        return NewsItemUIState(
            publisher = articleInBetween.publisher,
            author = articleInBetween.author,
            title = articleInBetween.title,
            thumbnail = articleInBetween.thumbnail,
            date = convertISODateToStringUseCase(articleInBetween.date),
            content = articleInBetween.content
        )
    }
}