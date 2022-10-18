package com.commanderpepper.pheme.ui.screens.article

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.ui.util.Loading
import com.commanderpepper.pheme.uistate.NewsItem
import com.commanderpepper.pheme.uistate.NewsItemExpanded
import com.commanderpepper.pheme.uistate.NewsItemUIState
import kotlinx.coroutines.flow.StateFlow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean,
    articleListViewModel: ArticleViewModel = hiltViewModel(),
    articleId: Long = -1L,
    onBackClicked: () -> Unit
) {
    /**
     * This boolean is used to check if the article was retrieved
     * Using rememberSaveable to persist the value across recompositions and configuration changes
     */
    val articleRetrieved = rememberSaveable {
        mutableStateOf(false)
    }
    if (articleRetrieved.value.not()) {
        articleListViewModel.retrieveArticle(articleId)
        articleRetrieved.value = true
    }
    Article(
        modifier = modifier,
        isExpandedScreen = isExpandedScreen,
        articleUIStateFlow = articleListViewModel.articleUIState,
        onBackClicked = onBackClicked
    )
}

@Composable
fun Article(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean,
    articleUIStateFlow: StateFlow<ArticleUIState>,
    onBackClicked: () -> Unit
) {
    val articleUIState: ArticleUIState by articleUIStateFlow.collectAsState()
    Scaffold(modifier = modifier,
        topBar = {
            val color = MaterialTheme.colors.primaryVariant
            TopAppBar(backgroundColor = color) {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Go back to article list"
                    )
                }
            }
        }) { _ ->
        when {
            articleUIState.isError -> {
                ArticleError()
            }
            articleUIState.isLoading -> {
                ArticleLoading()
            }
            articleUIState.isSuccess -> {
                if (isExpandedScreen) {
                    ArticleExpanded(
                        modifier = modifier,
                        newsItemUIState = articleUIState.newsItemUIState!!
                    )
                } else {
                    ArticleNormal(
                        modifier = modifier,
                        newsItemUIState = articleUIState.newsItemUIState!!
                    )
                }
            }
        }
    }

}

@Composable
fun ArticleNormal(modifier: Modifier = Modifier, newsItemUIState: NewsItemUIState) {
    NewsItem(modifier = modifier, newsItemUIState = newsItemUIState)
}

@Composable
fun ArticleExpanded(modifier: Modifier = Modifier, newsItemUIState: NewsItemUIState) {
    NewsItemExpanded(modifier = modifier, newsItemUIState = newsItemUIState)
}

@Composable
fun ArticleError(modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = stringResource(id = R.string.error_message))
}

@Composable
fun ArticleLoading(modifier: Modifier = Modifier) {
    Loading()
}