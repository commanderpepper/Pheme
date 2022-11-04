package com.commanderpepper.pheme.ui.screens.article

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.ui.util.Loading
import com.commanderpepper.pheme.ui.util.LoadingArticle
import com.commanderpepper.pheme.ui.util.LoadingArticleExpanded
import com.commanderpepper.pheme.ui.uistate.NewsItem
import com.commanderpepper.pheme.ui.uistate.NewsItemExpanded
import com.commanderpepper.pheme.ui.uistate.NewsItemUIState
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
            val color = MaterialTheme.colorScheme.primary
            TopAppBar(modifier = modifier.background(color), title = {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Go back to article list"
                    )
                }
            })
        }) { paddingValues ->
        when {
            articleUIState.isError -> {
                ArticleError()
            }
            articleUIState.isLoading -> {
                if(isExpandedScreen){
                    LoadingArticle()
                }else {
                    LoadingArticleExpanded()
                }
            }
            articleUIState.isSuccess -> {
                if (isExpandedScreen) {
                    ArticleExpanded(
                        modifier = modifier.padding(paddingValues),
                        newsItemUIState = articleUIState.newsItemUIState!!
                    )
                } else {
                    ArticleNormal(
                        modifier = modifier.padding(paddingValues),
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

@Composable
fun ArticleLoading(modifier: Modifier = Modifier) {
    Loading()
}

@Preview
@Composable
fun ArticleUIPreview(){
    ArticleError()
}