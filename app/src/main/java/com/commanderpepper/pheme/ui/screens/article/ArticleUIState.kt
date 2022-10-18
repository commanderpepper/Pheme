package com.commanderpepper.pheme.ui.screens.article

import com.commanderpepper.pheme.uistate.NewsItemUIState

data class ArticleUIState (
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val newsItemUIState: NewsItemUIState? = null)