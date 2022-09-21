package com.commanderpepper.pheme.ui.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import com.commanderpepper.pheme.ui.activities.article.ArticleActivity
import com.commanderpepper.pheme.ui.activities.home.theme.PhemeTheme
import com.commanderpepper.pheme.ui.activities.home.ui.DisplayHomeActivity
import com.commanderpepper.pheme.uistate.NewsPreviewItem
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val vm: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            vm.loadArticles()
        }

        setContent {
            PhemeTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DisplayHomeActivity(viewModel = vm, ::onArticleClicked, vm::categoryClicked)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhemeTheme {
        NewsPreviewItem(NewsPreviewItemUIState("New York Times",
            "John Doe",
            "The Weather",
            "Its sunny",
            "https://i.imgur.com/olisBgy.png",
            0L)){
        }
    }
}