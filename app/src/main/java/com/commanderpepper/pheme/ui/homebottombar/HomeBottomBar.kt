package com.commanderpepper.pheme.ui.homebottombar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.data.Category
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeBottomBar(categoryUIStateFlow: StateFlow<CategoryUIState>, backgroundColor : Color, onCategoryClicked: (Category) -> Unit){
    val category = categoryUIStateFlow.collectAsState()
    BottomAppBar(backgroundColor = backgroundColor) {
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

@Composable
private fun RowScope.CategoryButton(categoryButtonUIState: CategoryButtonUIState, buttonClick: (Category) -> Unit) {
    val contentDescription = stringResource(id = categoryButtonUIState.contentDescriptionId)
    BottomNavigationItem(
        icon = {
            val image = painterResource(id = categoryButtonUIState.resourceId)
            Icon(image, contentDescription)
        },
        selectedContentColor = Color.White,
        unselectedContentColor = Color.White.copy(alpha = 0.2f),
        onClick = {
            buttonClick(categoryButtonUIState.category)
        },
        selected = categoryButtonUIState.isSelected
    )
}