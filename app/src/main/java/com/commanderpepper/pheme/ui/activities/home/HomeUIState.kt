package com.commanderpepper.pheme.ui.activities.home

import com.commanderpepper.pheme.repository.local.Category
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState

data class HomeUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val newsPreviewList: List<NewsPreviewItemUIState> = emptyList(),
    val currentCategory: Category = Category.NEWS
)