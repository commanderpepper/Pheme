package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.uistate.NewsItemUIState
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import org.junit.Assert.*

import org.junit.Test

class ConvertArticleEntityToNewsItemUIStateTest {

    private val articleInBetween = ArticleInBetween(
        id = 0L,
        publisher = "Penguin",
        author = "John Doe",
        title = "This is a title alright",
        preview = "Preview",
        thumbnail = "A picture",
        date = "2022-10-03T19:10:00Z",
        content = "This is the content"
    )

    private val newsItemUIState = NewsItemUIState(
        publisher = "Penguin",
        author = "John Doe",
        title = "This is a title alright",
        thumbnail = "A picture",
        date = "October 3, 2022",
        content = "This is the content"
    )

    private val convertISODateToStringUseCase = ConvertISODateToStringUseCase()
    val convertArticleEntityToNewsItemUIState = ConvertArticleEntityToNewsItemUIState(convertISODateToStringUseCase)

    @Test
    operator fun invoke() {
        val createdConvertNewsItemUIState = convertArticleEntityToNewsItemUIState(articleInBetween)
        assertTrue(createdConvertNewsItemUIState == newsItemUIState)
    }
}