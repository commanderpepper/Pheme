package com.commanderpepper.pheme.ui.homebottombar

import com.commanderpepper.pheme.repository.local.Category

data class CategoryUIState(
    val currentCategory: Category = Category.NEWS
)