package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.data.Article
import com.commanderpepper.pheme.data.Category
import com.commanderpepper.pheme.data.Source
import com.commanderpepper.pheme.room.model.ArticleEntity
import org.junit.Assert.*

import org.junit.Test

class CreateArticleEntityUseCaseTest {

    private val category = Category.NEWS
    private val article = Article(
        source = Source(
            id = null,
            name = "New York Times"
        ),
        author = "John Doe",
        title = "What a title",
        description = "John Doe does a thing",
        url = "google.com",
        urlToImage = null,
        publishedAt = null,
        content = "He did something"
    )

    private val articleEntity = ArticleEntity(
        publisher = "New York Times",
        title = "What a title",
        thumbnail = "",
        preview = "John Doe does a thing",
        content = "He did something",
        category = category.category,
        author = "John Doe",
        publication = ""
    )

    val createArticleEntityUseCase = CreateArticleEntityUseCase()

    @Test
    fun `CREATE ARTICLEENTITY AND CHECK OBJECT`() {
        val articleEntityCreated = createArticleEntityUseCase(category, article)
        assertTrue(articleEntityCreated == articleEntity)
    }
}