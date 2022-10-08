package com.commanderpepper.pheme.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.commanderpepper.pheme.TestDispatcherRule
import com.commanderpepper.pheme.room.model.ArticleEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var articleDao: ArticleDAO
    private lateinit var database: ArticleDatabase

    private val articleCategory = "News"
    private val articleOne = ArticleEntity(publisher = "Android times", title = "This is a test", author = "John Doe", publication = "Kotlin", thumbnail = "picture.png", preview = "This an article", content = "This is an article for real", category = articleCategory)
    private val articleTwo = ArticleEntity(publisher = "Android times", title = "This is a test dos", author = "Jane Eyre", publication = "Java", thumbnail = "picture.png", preview = "This an article dos", content = "This is an article for real", category = articleCategory)

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Before
    fun create(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            ArticleDatabase::class.java
        ).build()
        articleDao = database.articleDao()
    }

    @After
    @Throws(IOException::class)
    fun cleanup(){
        database.close()
    }

    @Test
    fun writeArticleAndReadInList() = runTest {
        articleDao.insertArticles(listOf(articleOne))
        val returnedArticles = mutableListOf<ArticleEntity>()

        returnedArticles.addAll(articleDao.getArticles(articleCategory))
        Assert.assertTrue(returnedArticles.size > 0)
        Assert.assertTrue(returnedArticles.size == 1)
    }

    @Test
    fun writeTwoArticlesAndReadInList() = runTest {
        articleDao.insertArticles(listOf(articleOne, articleTwo))
        val returnedArticles = mutableListOf<ArticleEntity>()

        returnedArticles.addAll(articleDao.getArticles(articleCategory))
        Assert.assertTrue(returnedArticles.size > 1)
        Assert.assertTrue(returnedArticles.size == 2)
        Assert.assertTrue(returnedArticles.first() != returnedArticles.last())
    }

    @Test
    fun writeOneArticleAndRetrieveArticle() = runTest {
        val id = 1L
        articleDao.insertArticles(listOf(articleOne))
        val returnedArticle = articleDao.getArticle(id)

        Assert.assertTrue(returnedArticle == articleOne.copy(id = id))
    }

    @Test
    fun writeTheSameArticleTwiceAndRetrieveListOfOne() = runTest {
        articleDao.insertArticles(listOf(articleOne, articleOne))
        val returnedArticles = articleDao.getArticles(articleCategory)
        Assert.assertTrue(returnedArticles.size == 1)
    }

    @Test
    fun writeTwoArticlesThenDeleteDatabase() = runTest {
        articleDao.insertArticles(listOf(articleOne, articleTwo))
        val returnedArticles = mutableListOf<ArticleEntity>()

        articleDao.deleteArticles()

        returnedArticles.addAll(articleDao.getArticles(articleCategory))
        Assert.assertTrue(returnedArticles.size == 0)
    }
}