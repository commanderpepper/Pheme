package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.data.room.model.ArticleEntity
import com.commanderpepper.pheme.domain.usecase.ConvertArticleEntityToArticleInBetweenUseCase
import com.commanderpepper.pheme.domain.usecase.model.ArticleInBetween
import org.junit.Assert
import org.junit.Test

class ConvertArticleEntityToArticleInBetweenUseCaseTest {

    private val articleCategory = Category.NEWS
    private val articleEntity: ArticleEntity = ArticleEntity(
        publisher = "Android times",
        title = "This is a test",
        author = "John Doe",
        publication = "Kotlin",
        thumbnail = "picture.png",
        preview = "This an article",
        content = "This is an article for real",
        category = articleCategory.category
    )

    private val articleInBetween = ArticleInBetween(
        id = 0,
        publisher = articleEntity.publisher,
        author = articleEntity.author,
        title = articleEntity.title,
        preview = articleEntity.preview,
        thumbnail = articleEntity.thumbnail,
        date = articleEntity.publication,
        content = articleEntity.content
    )

    private val convertArticleEntityToArticleInBetweenUseCase =
        ConvertArticleEntityToArticleInBetweenUseCase()

    @Test
    fun `CREATE ARTICLEINBETWEEN USING USE CASE`() {
        val articleInBetweenFromUseCase =
            convertArticleEntityToArticleInBetweenUseCase(articleEntity = articleEntity)
        Assert.assertTrue(articleInBetweenFromUseCase == articleInBetween)
    }
}