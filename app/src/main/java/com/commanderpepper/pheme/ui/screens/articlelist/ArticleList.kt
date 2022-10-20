package com.commanderpepper.pheme.ui.screens.articlelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    onArticleClicked: (Long) -> Unit
) {
    val articleListUIState: ArticleListUIState by articleListUIStateFlow.collectAsState()
    if (articleListUIState.isError) {
        ArticleListError(modifier)
    } else if (articleListUIState.isLoading) {
        ArticleListLoading(modifier)
    } else {
        ArticleList(
            modifier = modifier,
            articleList = articleListUIState.newsPreviewList,
            onArticleClicked = onArticleClicked
        )
    }
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articleList: List<NewsPreviewItemUIState>,
    onArticleClicked: (Long) -> Unit
) {
    LazyColumn(modifier = modifier) {
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
fun ArticleListError(modifier: Modifier = Modifier) {
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
        Text(text = stringResource(id = R.string.error_message))
    }
}