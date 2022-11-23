package com.commanderpepper.pheme.ui.uistate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.commanderpepper.pheme.data.retrofit.model.Category

data class CategoryButtonUIState(
    val category: Category,
    @DrawableRes val resourceId: Int,
    @StringRes val contentDescriptionId: Int
)