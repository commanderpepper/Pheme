package com.commanderpepper.pheme.ui.screens.articlelist

import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState

sealed class ArticleListUIState {
    data class Articles(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val isSuccessful: Boolean = false,
        val newsPreviewList: List<NewsPreviewItemUIState> = emptyList(),
    ) : ArticleListUIState()

    data class Searching(
        val isLoading: Boolean = true,
        val isEmpty: Boolean = false,
        val isSuccessful: Boolean = false,
        val newsPreviewSearchList: List<NewsPreviewItemUIState> = emptyList()
    ) : ArticleListUIState()
}