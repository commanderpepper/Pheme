package com.commanderpepper.pheme.ui.activities.article.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.ui.activities.article.ArticleUIState
import com.commanderpepper.pheme.ui.activities.article.ArticleViewModel
import com.commanderpepper.pheme.uistate.NewsItem

@Composable
fun DisplayArticle(articleViewModel: ArticleViewModel) {
    val articleUIState: ArticleUIState by articleViewModel.articleUIState.collectAsState()
    if (articleUIState.isError) {
        Text(text = stringResource(id = R.string.error_message))
    } else if (articleUIState.isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (articleUIState.newsItemUIState != null) {
        NewsItem(articleUIState.newsItemUIState!!)
    }
}