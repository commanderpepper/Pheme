package com.commanderpepper.pheme.ui.uistate

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.commanderpepper.pheme.uistate.NEWS_PREVIEW_IMAGE_CONTENT_DESCRIPTION
import com.commanderpepper.pheme.uistate.NewsPreviewItem
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import org.junit.Rule
import org.junit.Test

class NewsPreviewItemUIStateTest {
    private val newsPreviewItemUIStateWithImage = NewsPreviewItemUIState(
        publisher = "Publisher",
        author = "John Doe",
        title = "Title",
        thumbnail = "image.png",
        id = 1L
    )

    private val newsPreviewItemUIStateWithoutImage = NewsPreviewItemUIState(
        publisher = "Publisher",
        author = "John Doe",
        title = "Title",
        thumbnail = "",
        id = 1L
    )

    private val newsPreviewItemUIStateWithAuthor = NewsPreviewItemUIState(
        publisher = "Publisher",
        author = "John Doe",
        title = "Title",
        thumbnail = "image.png",
        id = 1L
    )

    private val newsPreviewItemUIStateWithoutAuthor = NewsPreviewItemUIState(
        publisher = "Publisher",
        author = "",
        title = "Title",
        thumbnail = "image.png",
        id = 1L
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun create_newspreview_with_image_check_content(){
        val newsPreviewItemUIState = newsPreviewItemUIStateWithImage

        composeTestRule.setContent {
            NewsPreviewItem(newsPreviewItemUIState = newsPreviewItemUIState, onClick = {})
        }

        composeTestRule.onNodeWithText(newsPreviewItemUIState.publisher).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsPreviewItemUIState.author).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsPreviewItemUIState.title).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(NEWS_PREVIEW_IMAGE_CONTENT_DESCRIPTION).assertExists().assertIsDisplayed()
    }

    @Test
    fun create_newspreview_without_image_check_content(){
        val newsPreviewItemUIState = newsPreviewItemUIStateWithoutImage

        composeTestRule.setContent {
            NewsPreviewItem(newsPreviewItemUIState = newsPreviewItemUIState, onClick = {})
        }

        composeTestRule.onNodeWithText(newsPreviewItemUIState.publisher).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsPreviewItemUIState.author).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsPreviewItemUIState.title).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(NEWS_PREVIEW_IMAGE_CONTENT_DESCRIPTION).assertDoesNotExist()
    }

    @Test
    fun create_newspreview_with_author_check_content(){
        val newsPreviewItemUIState = newsPreviewItemUIStateWithAuthor

        composeTestRule.setContent {
            NewsPreviewItem(newsPreviewItemUIState = newsPreviewItemUIState, onClick = {})
        }

        composeTestRule.onNodeWithText(newsPreviewItemUIState.publisher).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsPreviewItemUIState.author).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsPreviewItemUIState.title).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(NEWS_PREVIEW_IMAGE_CONTENT_DESCRIPTION).assertExists().assertIsDisplayed()
    }

    @Test
    fun create_newspreview_without_author_check_content(){
        val newsPreviewItemUIState = newsPreviewItemUIStateWithoutAuthor

        composeTestRule.setContent {
            NewsPreviewItem(newsPreviewItemUIState = newsPreviewItemUIState, onClick = {})
        }

        composeTestRule.onNodeWithText(newsPreviewItemUIState.publisher).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(newsPreviewItemUIState.author).assertDoesNotExist()
        composeTestRule.onNodeWithText(newsPreviewItemUIState.title).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(NEWS_PREVIEW_IMAGE_CONTENT_DESCRIPTION).assertExists().assertIsDisplayed()
    }

}