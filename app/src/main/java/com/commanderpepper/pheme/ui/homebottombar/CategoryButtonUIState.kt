package com.commanderpepper.pheme.ui.homebottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.commanderpepper.pheme.repository.local.Category

data class CategoryButtonUIState(
    val category: Category,
    @DrawableRes val resourceId: Int,
    @StringRes val contentDescriptionId: Int,
    val isSelected: Boolean
)