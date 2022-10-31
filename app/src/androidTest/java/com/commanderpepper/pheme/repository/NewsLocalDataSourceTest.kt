package com.commanderpepper.pheme.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.commanderpepper.pheme.TestDispatcherRule
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.data.repository.local.NewsLocalDataSource
import com.commanderpepper.pheme.data.repository.local.NewsLocalDataSourceImpl
import com.commanderpepper.pheme.data.room.ArticleDatabase
import com.commanderpepper.pheme.data.room.ArticleDAO
import com.commanderpepper.pheme.data.room.model.ArticleEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NewsLocalDataSourceTest {

    private lateinit var articleDao: ArticleDAO
    private lateinit var database: ArticleDatabase
    private lateinit var newsLocalDataSource: NewsLocalDataSource

    private val articleCategory = Category.NEWS.category
    private val articleEntityOne = ArticleEntity(publisher = "Android times", title = "This is a test", author = "John Doe", publication = "Kotlin", thumbnail = "picture.png", preview = "This an article", content = "This is an article for real", category = articleCategory)
    private val articleEntityTwo = ArticleEntity(publisher = "Android times", title = "This is a test dos", author = "Jane Eyre", publication = "Java", thumbnail = "picture.png", preview = "This an article dos", content = "This is an article for real", category = articleCategory)

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun create(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            ArticleDatabase::class.java
        ).build()
        articleDao = database.articleDao()
        newsLocalDataSource = NewsLocalDataSourceImpl(articleDao, dispatcherRule.testDispatcher)
    }

    @After
    @Throws(IOException::class)
    fun cleanup(){
        database.close()
    }

    @Test
    fun GIVEN_empty_database_WHEN_insert_two_articles_THEN_retrieve_two_articles() = runTest {
        newsLocalDataSource.insertArticles(listOf(articleEntityOne, articleEntityTwo))
        val returnedArticleEntities = newsLocalDataSource.getArticles(Category.NEWS)
        Assert.assertTrue(returnedArticleEntities.size == 2)
    }

    @Test
    fun GIVEN_empty_database_WHEN_insert_two_articles_THEN_retrieve_one_article() = runTest {
        val id = 1L
        newsLocalDataSource.insertArticles(listOf(articleEntityOne, articleEntityTwo))
        val articleEntity = newsLocalDataSource.getArticle(id)
        Assert.assertTrue(articleEntity == articleEntityOne.copy(id = id))
    }

    @Test
    fun GIVEN_empty_database_WHEN_insert_one_hundred_articles_and_delete_articles_THEN_retrieve_sixty_articles() = runTest {
            val list = mutableListOf<ArticleEntity>()
            repeat(100) {
                list.add(
                    articleEntityOne.copy(
                        title = articleEntityOne.title + "$it",
                        author = articleEntityOne.author + "$it"
                    )
                )
            }

            newsLocalDataSource.insertArticles(list)
            val articleEntitiesBeforeDeletion = newsLocalDataSource.getArticles(Category.NEWS)
            Assert.assertTrue(articleEntitiesBeforeDeletion.size == 100)
            Assert.assertTrue(articleEntitiesBeforeDeletion.isEmpty().not())

            newsLocalDataSource.deleteArticles(category = Category.NEWS, amountToDelete = 40)

            val articleEntitiesAfterDeletion = newsLocalDataSource.getArticles(Category.NEWS)
            Assert.assertTrue(articleEntitiesAfterDeletion.size == 60)
        }
}