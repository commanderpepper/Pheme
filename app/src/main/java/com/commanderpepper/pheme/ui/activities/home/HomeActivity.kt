package com.commanderpepper.pheme.ui.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import com.commanderpepper.pheme.ui.activities.article.ArticleActivity
import com.commanderpepper.pheme.ui.activities.home.theme.PhemeTheme
import com.commanderpepper.pheme.uistate.NewsPreviewItem
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val vm: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            vm.fetchNews()
        }

        setContent {
            PhemeTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DisplayHomeActivity(viewModel = vm, ::onArticleClicked)
                }
            }
        }
    }

    private fun onArticleClicked(id: Long){
        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(ARTICLE_INTENT_ID, id)
        startActivity(this, intent, null)
    }

    companion object {
        const val ARTICLE_INTENT_ID = "ArticleId"
    }
}

@Composable
fun DisplayHomeActivity(viewModel: HomeViewModel, onArticleClicked: (Long) -> Unit){
    val homeUIState : HomeUIState by viewModel.homeUIState.collectAsState()
    if(homeUIState.isError){
        Text(text = "Something went wrong")
    }
    if(homeUIState.isLoading || homeUIState.newsPreviewList.isEmpty()){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator()
        }
    }
    if(homeUIState.newsPreviewList.isNotEmpty()){
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(homeUIState.newsPreviewList, itemContent = {
                    item -> NewsPreviewItem(item, onClick = onArticleClicked)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhemeTheme {
        NewsPreviewItem(NewsPreviewItemUIState("New York Times",
            "John Doe",
            "The Weather",
            "Its sunny",
            "https://example.com/image.jpg",
            0L)){

        }
    }
}