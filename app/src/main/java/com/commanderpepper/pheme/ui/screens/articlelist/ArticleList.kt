package com.commanderpepper.pheme.ui.screens.articlelist

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.ui.util.LoadingArticles
import com.commanderpepper.pheme.uistate.NewsPreviewItem
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Articles(
    modifier: Modifier = Modifier,
    articleListUIStateFlow: StateFlow<ArticleListUIState>,
    lazyListState: LazyListState,
    onArticleClicked: (Long) -> Unit
) {
    val articleListUIState: ArticleListUIState by articleListUIStateFlow.collectAsState()
    when (articleListUIState) {
        is ArticleListUIState.Articles -> {
            when {
                (articleListUIState as ArticleListUIState.Articles).isError -> ArticleListError(
                    modifier,
                    R.string.articles_fetched_error_message
                )
                (articleListUIState as ArticleListUIState.Articles).isLoading -> ArticleListLoading(
                    modifier
                )
                (articleListUIState as ArticleListUIState.Articles).isSuccessful -> {
                    ArticleList(
                        modifier = modifier,
                        articleList = (articleListUIState as ArticleListUIState.Articles).newsPreviewList,
                        lazyListState = lazyListState,
                        onArticleClicked = onArticleClicked
                    )
                }
            }
        }
        is ArticleListUIState.Searching -> {
            when {
                (articleListUIState as ArticleListUIState.Searching).isEmpty -> ArticleListError(
                    modifier,
                    R.string.articles_searched_error_message
                )
                (articleListUIState as ArticleListUIState.Searching).isLoading -> ArticleListLoading(
                    modifier
                )
                (articleListUIState as ArticleListUIState.Searching).isSuccessful -> {
                    ArticleList(
                        modifier = modifier,
                        articleList = (articleListUIState as ArticleListUIState.Searching).newsPreviewSearchList,
                        lazyListState = lazyListState,
                        onArticleClicked = onArticleClicked
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articleList: List<NewsPreviewItemUIState>,
    lazyListState: LazyListState,
    onArticleClicked: (Long) -> Unit
) {
    LazyColumn(modifier = modifier, state = lazyListState) {
        items(items = articleList, itemContent = { item ->
            NewsPreviewItem(newsPreviewItemUIState = item, onClick = onArticleClicked)
        })
    }
}

@Composable
fun ArticleListLoading(modifier: Modifier = Modifier) {
    LoadingArticles()
}

@Composable
fun ArticleListError(modifier: Modifier = Modifier, @StringRes errorMessage: Int) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val image = painterResource(id = R.drawable.pheme_portrait_)
        Image(
            modifier = Modifier.clip(CircleShape),
            painter = image,
            contentDescription = "Error Image"
        )
        Text(text = stringResource(id = errorMessage))
    }
}