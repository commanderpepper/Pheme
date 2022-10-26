package com.commanderpepper.pheme.ui.uistate

import android.R
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.commanderpepper.pheme.data.Category
import com.commanderpepper.pheme.uistate.CategoryButton
import com.commanderpepper.pheme.uistate.CategoryButtonUIState
import org.junit.Rule
import org.junit.Test

class CategoryButtonUIStateTest {

    private val categoryButtonUIStateSelected: CategoryButtonUIState = CategoryButtonUIState(
        category = Category.NEWS,
        resourceId = R.drawable.ic_input_add,
        contentDescriptionId = R.string.copy,
        isSelected = true
    )
    private val categoryButtonUIStateUnselected = CategoryButtonUIState(
        category = Category.NEWS,
        resourceId = R.drawable.ic_input_add,
        contentDescriptionId = R.string.copy,
        isSelected = false
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun create_categorybutton_selected_check_content() {
        val categoryButtonUIState = categoryButtonUIStateSelected

        composeTestRule.setContent {
            Row {
                CategoryButton(categoryButtonUIState) {}
            }
        }

        composeTestRule.onRoot().printToLog(CategoryButtonUIStateTest::class.simpleName!!)

        composeTestRule.onNodeWithContentDescription("Copy").assertExists()
        composeTestRule.onRoot().onChild().assertIsSelected()
    }

    @Test
    fun create_categorybutton_unselected_check_content() {
        val categoryButtonUIState = categoryButtonUIStateUnselected

        composeTestRule.setContent {
            Row {
                CategoryButton(categoryButtonUIState) {}
            }
        }

        composeTestRule.onRoot().printToLog(CategoryButtonUIStateTest::class.simpleName!!)

        composeTestRule.onNodeWithContentDescription("Copy").assertExists()
        composeTestRule.onRoot().onChild().assertIsNotSelected()
    }
}