package com.commanderpepper.pheme.ui.screens.articlelist

import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState

sealed interface ArticleListUIState {
    object Loading: ArticleListUIState
    data class Error(val message: String): ArticleListUIState
    data class Success(val newsPreviewList: List<NewsPreviewItemUIState>): ArticleListUIState
}