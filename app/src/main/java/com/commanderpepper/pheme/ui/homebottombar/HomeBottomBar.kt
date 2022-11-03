package com.commanderpepper.pheme.ui.homebottombar

import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.retrofit.model.Category
import com.commanderpepper.pheme.ui.uistate.CategoryButton
import com.commanderpepper.pheme.ui.uistate.CategoryButtonUIState
import com.commanderpepper.pheme.ui.uistate.CategoryUIState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeBottomBar(categoryUIStateFlow: StateFlow<CategoryUIState>, backgroundColor : Color, onCategoryClicked: (Category) -> Unit){
    val category = categoryUIStateFlow.collectAsState()
    BottomAppBar(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
        CategoryButton(
            CategoryButtonUIState(
            category = Category.NEWS,
            resourceId = R.drawable.ic_news,
            contentDescriptionId = R.string.home_activity_bottom_bar_news_content_description,
            isSelected = category.value.currentCategory == Category.NEWS
        ),
            buttonClick = {
                onCategoryClicked(Category.NEWS)
            }
        )
        CategoryButton(
            CategoryButtonUIState(
            category = Category.TECH,
            resourceId = R.drawable.ic_technology,
            contentDescriptionId = R.string.home_activity_bottom_bar_technology_content_description,
            isSelected = category.value.currentCategory == Category.TECH
        ),
            buttonClick = {
                onCategoryClicked(Category.TECH)
            }
        )
        CategoryButton(
            CategoryButtonUIState(
            category = Category.ENTERTAINMENT,
            resourceId = R.drawable.ic_entertainment,
            contentDescriptionId = R.string.home_activity_bottom_bar_entertainment_content_description,
            isSelected = category.value.currentCategory == Category.ENTERTAINMENT
        ),
            buttonClick = {
                onCategoryClicked(Category.ENTERTAINMENT)
            }
        )
        CategoryButton(
            CategoryButtonUIState(
            category = Category.SPORTS,
            resourceId = R.drawable.ic_sports,
            contentDescriptionId = R.string.home_activity_bottom_bar_sports_content_description,
            isSelected = category.value.currentCategory == Category.SPORTS
        ),
            buttonClick = {
                onCategoryClicked(Category.SPORTS)
            }
        )
    }
}