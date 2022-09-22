package com.commanderpepper.pheme.ui.activities.article.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.ui.activities.article.ArticleUIState
import com.commanderpepper.pheme.ui.activities.article.ArticleViewModel
import com.commanderpepper.pheme.ui.util.Loading
import com.commanderpepper.pheme.uistate.NewsItem

@Composable
fun DisplayArticle(articleViewModel: ArticleViewModel, onBackClicked: () -> Unit) {
    val articleUIState: ArticleUIState by articleViewModel.articleUIState.collectAsState()
    Scaffold(topBar = {
        val color = MaterialTheme.colors.primaryVariant
        TopAppBar(backgroundColor = color) {
            IconButton(onClick = { onBackClicked() }) {
                Icon( imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back to article list")
            }
        }
    }) { _ ->
        if (articleUIState.isError) {
            Text(text = stringResource(id = R.string.error_message))
        } else if (articleUIState.isLoading) {
            LoadingArticle()
        } else if (articleUIState.newsItemUIState != null) {
            NewsItem(articleUIState.newsItemUIState!!)
        }
    }
}

@Composable
fun LoadingArticle(){
    Loading()
}

@Composable
fun ArticleTopBar(onBackClicked: () -> Unit){

}