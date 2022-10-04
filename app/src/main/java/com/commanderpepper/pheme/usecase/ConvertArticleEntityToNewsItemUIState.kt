package com.commanderpepper.pheme.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.commanderpepper.pheme.uistate.NewsItemUIState
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import javax.inject.Inject

class ConvertArticleEntityToNewsItemUIState @Inject constructor(val createDateUseCase: CreateDateUseCase) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(articleInBetween: ArticleInBetween): NewsItemUIState {
        return NewsItemUIState(
            publisher = articleInBetween.publisher,
            author = articleInBetween.author,
            title = articleInBetween.title,
            thumbnail = articleInBetween.thumbnail,
            date = createDateUseCase(articleInBetween.date),
            content = articleInBetween.content
        )
    }
}