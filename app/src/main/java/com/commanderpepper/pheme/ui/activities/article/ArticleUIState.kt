package com.commanderpepper.pheme.ui.activities.article

import com.commanderpepper.pheme.uistate.NewsItemUIState

data class ArticleUIState (
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val newsItemUIState: NewsItemUIState? = null)