package com.commanderpepper.pheme.ui.uistate

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.commanderpepper.pheme.ui.uistate.NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION
import com.commanderpepper.pheme.ui.uistate.NewsItem
import com.commanderpepper.pheme.ui.uistate.NewsItemExpanded
import com.commanderpepper.pheme.ui.uistate.NewsItemUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsItemUIStateTest {

    private val newsItemUIStateWithImage = NewsItemUIState(
        publisher = "New York Times",
        author = "John Doe",
        title = "Title",
        thumbnail = "https://i.imgur.com/olisBgy.png",
        date = "Jan 1, 2000",
        content = "content"
    )

    private val newsItemUIStateWithOutImage = NewsItemUIState(
        publisher = "New York Times",
        author = "John Doe",
        title = "Title",
        thumbnail = "",
        date = "Jan 1, 2000",
        content = "content"
    )

    private lateinit var coroutineScope : CoroutineScope

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUpCoroutineScope(){
        coroutineScope = CoroutineScope(SupervisorJob())
    }

    @After
    fun cleanUp(){
        coroutineScope.cancel()
    }

    @Test
    fun create_newsitem_ui_with_image_ui_check_content(){
        val newsItemUIState = newsItemUIStateWithImage

        composeTestRule.setContent {
            NewsItem(newsItemUIState = newsItemUIState)
        }

        coroutineScope.launch {
            composeTestRule.awaitIdle()

            composeTestRule.onNodeWithText(newsItemUIState.publisher).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.author).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.title).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.date).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.content).assertExists().assertIsDisplayed()

            composeTestRule.onNodeWithContentDescription(NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION).assertExists().assertIsDisplayed()
        }
    }

    @Test
    fun create_newsitem_ui_without_image_check_content(){
        val newsItemUIState = newsItemUIStateWithOutImage

        composeTestRule.setContent {
            NewsItem(newsItemUIState = newsItemUIState)
        }

        coroutineScope.launch {
            composeTestRule.awaitIdle()

            composeTestRule.onNodeWithText(newsItemUIState.publisher).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.author).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.title).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.date).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.content).assertExists().assertIsDisplayed()

            composeTestRule.onNodeWithContentDescription(NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION).assertDoesNotExist()
        }
    }

    @Test
    fun create_newsitem_expanded_ui_with_image_ui_check_content(){
        val newsItemUIState = newsItemUIStateWithImage

        composeTestRule.setContent {
            NewsItemExpanded(newsItemUIState = newsItemUIState)
        }

        coroutineScope.launch {
            composeTestRule.awaitIdle()

            composeTestRule.onNodeWithText(newsItemUIState.publisher).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.author).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.title).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.date).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.content).assertExists().assertIsDisplayed()

            composeTestRule.onNodeWithContentDescription(NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION).assertExists().assertIsDisplayed()
        }
    }

    @Test
    fun create_newsitem_expanded_ui_without_image_check_content(){
        val newsItemUIState = newsItemUIStateWithOutImage

        composeTestRule.setContent {
            NewsItemExpanded(newsItemUIState = newsItemUIState)
        }

        coroutineScope.launch {
            composeTestRule.awaitIdle()

            composeTestRule.onNodeWithText(newsItemUIState.publisher).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.author).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.title).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.date).assertExists().assertIsDisplayed()
            composeTestRule.onNodeWithText(newsItemUIState.content).assertExists().assertIsDisplayed()

            composeTestRule.onNodeWithContentDescription(NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION).assertDoesNotExist()
        }
    }
}