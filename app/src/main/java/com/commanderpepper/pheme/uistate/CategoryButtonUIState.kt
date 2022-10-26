package com.commanderpepper.pheme.uistate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.commanderpepper.pheme.data.Category

data class CategoryButtonUIState(
    val category: Category,
    @DrawableRes val resourceId: Int,
    @StringRes val contentDescriptionId: Int,
    val isSelected: Boolean
)

@Composable
fun RowScope.CategoryButton(categoryButtonUIState: CategoryButtonUIState, buttonClick: (Category) -> Unit) {
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