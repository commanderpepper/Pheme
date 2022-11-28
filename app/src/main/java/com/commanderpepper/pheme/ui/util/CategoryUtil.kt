package com.commanderpepper.pheme.ui.util

import androidx.compose.runtime.Composable
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.ui.uistate.CategoryButtonUIState

@Composable
fun createCategoryButtonUIStates(): List<CategoryButtonUIState> {
    val bottomNavBarIcons = listOf(
        R.drawable.ic_news,
        R.drawable.ic_sports,
        R.drawable.ic_technology,
        R.drawable.ic_entertainment,
    )

    val navContentDescription = listOf(
        R.string.home_activity_bottom_bar_news_content_description,
        R.string.home_activity_bottom_bar_sports_content_description,
        R.string.home_activity_bottom_bar_technology_content_description,
        R.string.home_activity_bottom_bar_entertainment_content_description
    )

    val categories = Category.values()

    val categoryButtonUIStateList = List(4) { i ->
        CategoryButtonUIState(
            category = categories[i],
            resourceId = bottomNavBarIcons[i],
            contentDescriptionId = navContentDescription[i]
        )
    }
    return categoryButtonUIStateList
}