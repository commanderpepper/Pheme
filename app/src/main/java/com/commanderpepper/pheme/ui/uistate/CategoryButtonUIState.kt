package com.commanderpepper.pheme.ui.uistate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.commanderpepper.pheme.data.retrofit.model.Category

data class CategoryButtonUIState(
    val category: Category,
    @DrawableRes val resourceId: Int,
    @StringRes val contentDescriptionId: Int,
    val isSelected: Boolean
)

@Composable
fun RowScope.CategoryButton(categoryButtonUIState: CategoryButtonUIState, buttonClick: (Category) -> Unit) {
    val contentDescription = stringResource(id = categoryButtonUIState.contentDescriptionId)
    NavigationBarItem(selected = categoryButtonUIState.isSelected,
        onClick = { buttonClick(categoryButtonUIState.category) },
        icon = {
            val image = painterResource(id = categoryButtonUIState.resourceId)
            Icon(image, contentDescription)
        },
        interactionSource = MutableInteractionSource()
    )
}