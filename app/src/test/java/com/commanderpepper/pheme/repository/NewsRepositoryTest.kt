package com.commanderpepper.pheme.repository

import app.cash.turbine.test
import com.commanderpepper.pheme.data.repository.Status
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.data.repository.local.NewsLocalDataSource
import com.commanderpepper.pheme.data.repository.remote.NewsRemoteDataSource
import com.commanderpepper.pheme.data.repository.repos.NewsRepository
import com.commanderpepper.pheme.data.repository.repos.NewsRepositoryImpl
import com.commanderpepper.pheme.domain.usecase.ConvertArticleEntityToArticleInBetweenUseCase
import com.commanderpepper.pheme.domain.usecase.CreateArticleEntityUseCase
import com.commanderpepper.pheme.util.StringProvider
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.stub

@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {

    @Mock
    private val newsLocalDataSource = mock(NewsLocalDataSource::class.java)

    @Mock
    private val newsRemoteDataSource = mock(NewsRemoteDataSource::class.java)

    @Mock
    private val stringProvider = mock(StringProvider::class.java)

    private val createArticleEntityUseCase: CreateArticleEntityUseCase =
        CreateArticleEntityUseCase()
    private val convertArticleEntityToArticleInBetweenUseCase =
        ConvertArticleEntityToArticleInBetweenUseCase()
    private val newsRepository: NewsRepository = NewsRepositoryImpl(
        newsLocalDataSource,
        newsRemoteDataSource,
        createArticleEntityUseCase,
        convertArticleEntityToArticleInBetweenUseCase,
        stringProvider
    )

    private val stringProviderResource = 0
    private val stringProviderString = "This is a test"
    private val category = Category.NEWS

    @Before
    fun setUpMocks() {
        newsLocalDataSource.stub {
            onBlocking {
                getArticles(category)
            }.thenReturn(emptyList())
        }

        newsRemoteDataSource.stub {
            onBlocking {
                retrieveCategoryArticles(category.category)
            }.thenReturn(emptyList())
        }

        Mockito.`when`(stringProvider.getString(stringProviderResource))
            .thenReturn(stringProviderString)
    }

    @Test
    fun `TEST GET ARTICLES`() = runBlocking {
        val articles = newsRepository.fetchArticles(category)
        articles.test {
            assertEquals(Status.InProgress, awaitItem())
            assertTrue(awaitItem() is Status.Failure)

//            assertSame(Status.Failure("Something went wrong"), awaitItem())
//            awaitComplete()
            cancelAndIgnoreRemainingEvents()
        }
    }


}