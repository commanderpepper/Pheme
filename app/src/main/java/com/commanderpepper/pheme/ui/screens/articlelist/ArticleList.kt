package com.commanderpepper.pheme.ui.screens.articlelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.ui.hometopbar.HomeTopBar
import com.commanderpepper.pheme.ui.main.HomeFloatingActionButton
import com.commanderpepper.pheme.ui.main.MainViewModel
import com.commanderpepper.pheme.ui.uistate.NewsPreviewItem
import com.commanderpepper.pheme.ui.uistate.NewsPreviewItemUIState
import com.commanderpepper.pheme.ui.util.LoadingArticles
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Articles(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onArticleClicked: (Long) -> Unit
){
    Articles(
        modifier = modifier,
        articleListUIStateFlow = mainViewModel.articleUIListStateFlow,
        onArticleClicked = onArticleClicked,
        navigationText = mainViewModel.searchQueryFlow,
        categoryText = mainViewModel.homeTopAppBarCategoryTextFlow,
        onTextChange = mainViewModel::searchArticles,
        onAction = mainViewModel::clearSearch
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Articles(
    modifier: Modifier = Modifier,
    articleListUIStateFlow: StateFlow<ArticleListUIState>,
    onArticleClicked: (Long) -> Unit,
    navigationText: StateFlow<String>,
    categoryText: StateFlow<String>,
    onTextChange: (String) -> Unit,
    onAction: () -> Unit
) {
    val articleListUIState: ArticleListUIState by articleListUIStateFlow.collectAsState()
    Column(modifier = modifier) {
        HomeTopBar(
            navigationIcon = Icons.Rounded.Search,
            navigationIconContentDescription = null,
            actionIcon = Icons.Rounded.Clear,
            actionIconContentDescription = null,
            navigationText = navigationText,
            categoryText = categoryText,
            onTextChange = onTextChange,
            onAction = onAction
        )
        when (articleListUIState) {
            is ArticleListUIState.Error -> ArticleListError(
                modifier,
                (articleListUIState as ArticleListUIState.Error).message
            )
            is ArticleListUIState.Loading -> ArticleListLoading(modifier)
            is ArticleListUIState.Success -> ArticleList(
                modifier = modifier,
                articleList = (articleListUIState as ArticleListUIState.Success).newsPreviewList,
                onArticleClicked = onArticleClicked
            )
        }
    }
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articleList: List<NewsPreviewItemUIState>,
    onArticleClicked: (Long) -> Unit
) {
    val lazyListState = rememberLazyListState()
    Box(modifier = modifier.fillMaxHeight()) {
        LazyColumn(modifier = modifier.fillMaxHeight(), state = lazyListState) {
            items(items = articleList, itemContent = { item ->
                NewsPreviewItem(newsPreviewItemUIState = item, onClick = onArticleClicked)
            })
        }
        HomeFloatingActionButton(modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp), lazyListState = lazyListState)
    }

}

@Composable
fun ArticleListLoading(modifier: Modifier = Modifier) {
    LoadingArticles(modifier)
}

@Composable
fun ArticleListError(modifier: Modifier = Modifier, errorMessage: String) {
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
        Text(text = errorMessage)
    }
}