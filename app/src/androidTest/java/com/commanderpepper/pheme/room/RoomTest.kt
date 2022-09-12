package com.commanderpepper.pheme.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.commanderpepper.pheme.TestDispatcherRule
import com.commanderpepper.pheme.room.model.ArticleDAO
import com.commanderpepper.pheme.room.model.ArticleEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var articleDao: ArticleDAO
    private lateinit var database: ArticleDatabase

    private val articleCategory = "News"
    private val articleOne = ArticleEntity(publisher = "Android times", title = "This is a test", thumbnail = "picture.png", preview = "This an article", content = "This is an article for real", category = articleCategory)
    private val articleTwo = ArticleEntity(publisher = "Android times", title = "This is a test dos", thumbnail = "picture.png", preview = "This an article dos", content = "This is an article for real", category = articleCategory)

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

        articleDao.getArticles(articleCategory).test {
            returnedArticles.addAll(this.awaitItem())
            Assert.assertTrue(returnedArticles.size > 0)
            Assert.assertTrue(returnedArticles.size == 1)
            cancel()
        }
    }

    @Test
    fun writeTwoArticlesAndReadInList() = runTest {
        articleDao.insertArticles(listOf(articleOne, articleTwo))
        val returnedArticles = mutableListOf<ArticleEntity>()

        articleDao.getArticles(articleCategory).test {
            returnedArticles.addAll(this.awaitItem())
            Assert.assertTrue(returnedArticles.size > 1)
            Assert.assertTrue(returnedArticles.size == 2)
            Assert.assertTrue(returnedArticles.first() != returnedArticles.last())
            cancel()
        }
    }

    @Test
    fun writeTwoArticlesThenDeleteDatabase() = runTest {
        articleDao.insertArticles(listOf(articleOne, articleTwo))
        val returnedArticles = mutableListOf<ArticleEntity>()

        articleDao.deleteArticles()

        articleDao.getArticles(articleCategory).test {
            returnedArticles.addAll(this.awaitItem())
            Assert.assertTrue(returnedArticles.size == 0)
            cancel()
        }
    }
}