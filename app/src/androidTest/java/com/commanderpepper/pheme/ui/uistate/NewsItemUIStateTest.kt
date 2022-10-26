package com.commanderpepper.pheme.ui.uistate

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.core.graphics.rotationMatrix
import com.commanderpepper.pheme.uistate.*
import org.junit.Rule
import org.junit.Test

class NewsItemUIStateTest {

    private val newsItemUIStateWithImage = NewsItemUIState(
        publisher = "New York Times",
        author = "John Doe",
        title = "Title",
        thumbnail = "image.png",
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

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun create_newsitem_ui_with_image_ui_check_content(){
        val newsItemUIState = newsItemUIStateWithImage

        composeTestRule.setContent {
            NewsItem(newsItemUIState = newsItemUIState)
            rotationMatrix(90F)
        }

        composeTestRule.onNodeWithText(newsItemUIState.publisher).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.author).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.title).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.date).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.content).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION).assertExists().assertIsDisplayed()
    }

    @Test
    fun create_newsitem_ui_without_image_check_content(){
        val newsItemUIState = newsItemUIStateWithOutImage

        composeTestRule.setContent {
            NewsItem(newsItemUIState = newsItemUIState)
        }

        composeTestRule.onNodeWithText(newsItemUIState.publisher).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.author).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.title).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.date).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.content).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION).assertDoesNotExist()
    }

    @Test
    fun create_newsitem_expanded_ui_with_image_ui_check_content(){
        val newsItemUIState = newsItemUIStateWithImage

        composeTestRule.setContent {
            NewsItemExpanded(newsItemUIState = newsItemUIState)
        }

        composeTestRule.onNodeWithText(newsItemUIState.publisher).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.author).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.title).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.date).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.content).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION).assertExists().assertIsDisplayed()
    }

    @Test
    fun create_newsitem_expanded_ui_without_image_check_content(){
        val newsItemUIState = newsItemUIStateWithOutImage

        composeTestRule.setContent {
            NewsItemExpanded(newsItemUIState = newsItemUIState)
        }

        composeTestRule.onNodeWithText(newsItemUIState.publisher).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.author).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.title).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.date).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsItemUIState.content).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION).assertDoesNotExist()
    }
}