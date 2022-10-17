package com.commanderpepper.pheme.ui.screens.articlelist

import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState

data class ArticleListUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val newsPreviewList: List<NewsPreviewItemUIState> = emptyList()
)