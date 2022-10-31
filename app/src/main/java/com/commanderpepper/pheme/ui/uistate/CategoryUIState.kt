package com.commanderpepper.pheme.ui.uistate

import com.commanderpepper.pheme.data.retrofit.model.Category

data class CategoryUIState(
    val currentCategory: Category = Category.NEWS
)