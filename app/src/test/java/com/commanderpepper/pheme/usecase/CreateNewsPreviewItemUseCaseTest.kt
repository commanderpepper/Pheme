package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.usecase.model.ArticleInBetween
import org.junit.Assert
import org.junit.Test

class CreateNewsPreviewItemUseCaseTest {

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

    private val newsPreviewItemUIState = NewsPreviewItemUIState(
        publisher = "Penguin",
        author = "John Doe",
        title = "This is a title alright",
        thumbnail = "A picture",
        id = 0L
    )

    val createNewsPreviewItemUseCase = CreateNewsPreviewItemUseCase()

    @Test
    fun `CREATE NEWSPREVIEWITEMUISTATE AND CHECK OBJECT`(){
        val createdNewsPreviewItemUIState = createNewsPreviewItemUseCase(articleInBetween)
        Assert.assertTrue(newsPreviewItemUIState == createdNewsPreviewItemUIState)
    }
}