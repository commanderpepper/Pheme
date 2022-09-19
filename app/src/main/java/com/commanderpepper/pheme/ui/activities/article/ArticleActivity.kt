package com.commanderpepper.pheme.ui.activities.article

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.commanderpepper.pheme.ui.activities.article.ui.theme.PhemeTheme
import com.commanderpepper.pheme.ui.activities.home.HomeActivity.Companion.ARTICLE_INTENT_ID
import com.commanderpepper.pheme.uistate.NewsItem
import com.commanderpepper.pheme.uistate.NewsItemUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleActivity : ComponentActivity() {

    private val vm: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = this.intent
        val id = intent.getLongExtra(ARTICLE_INTENT_ID, 0L)
        setContent {
            PhemeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    vm.retrieveArticle(id)
                    DisplayArticle(vm)
                }
            }
        }
    }
}

@Composable
fun DisplayArticle(articleViewModel: ArticleViewModel) {
    val articleUIState: ArticleUIState by articleViewModel.articleUIState.collectAsState()
    if (articleUIState.isError) {
        Text(text = "Something went wrong")
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

@Preview(showBackground = true)
@Composable
fun NewsItemPreview() {
    PhemeTheme {
        NewsItem(
            NewsItemUIState(
                publisher = "New York Times",
                author = "Author",
                title = "This is an incredible article",
                thumbnail = "https://i.imgur.com/olisBgy.png",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
            )
        )
    }
}