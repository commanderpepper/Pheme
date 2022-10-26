package com.commanderpepper.pheme.uistate

import com.commanderpepper.pheme.data.Category

data class CategoryUIState(
    val currentCategory: Category = Category.NEWS
)