package com.commanderpepper.pheme.ui.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.repository.local.Category
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

@Composable
fun DisplayHomeActivity(viewModel: HomeViewModel, onArticleClicked: (Long) -> Unit, onCategoryClicked: (Category) -> Unit){
    Scaffold(
        bottomBar = { val color = colorResource(id = R.color.bottom_app_color)
            HomeBottomBar(backgroundColor = color, onCategoryClicked) }
    ) {
        val homeUIState : HomeUIState by viewModel.homeUIState.collectAsState()
        if(homeUIState.isError){
            Text(text = "Something went wrong")
        }
        else if(homeUIState.isLoading || homeUIState.newsPreviewList.isEmpty()){
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
        else if(homeUIState.newsPreviewList.isNotEmpty()){
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(homeUIState.newsPreviewList, itemContent = {
                        item -> NewsPreviewItem(item, onClick = onArticleClicked)
                })
            }
        }
    }
}

@Composable
fun HomeBottomBar(backgroundColor : Color, onCategoryClicked: (Category) -> Unit){
    var category : Category by rememberSaveable { mutableStateOf(Category.NEWS) }
    BottomAppBar(backgroundColor = backgroundColor) {
        CategoryButton(
            category = Category.NEWS,
            resourceId = R.drawable.ic_news,
            contentDescription = "News",
            isSelected = category == Category.NEWS,
            buttonClick = {
                category = Category.NEWS
                onCategoryClicked(category)
            }
        )
        CategoryButton(
            category = Category.TECH,
            resourceId = R.drawable.ic_technology,
            contentDescription = "Technology",
            isSelected = category == Category.TECH,
            buttonClick = {
                category = Category.TECH
                onCategoryClicked(category)
            }
        )
        CategoryButton(
            category = Category.ENTERTAINMENT,
            resourceId = R.drawable.ic_entertainment,
            contentDescription = "Entertainment",
            isSelected = category == Category.ENTERTAINMENT,
            buttonClick = {
                category = Category.ENTERTAINMENT
                onCategoryClicked(category)
            }
        )
        CategoryButton(
            category = Category.SPORTS,
            resourceId = R.drawable.ic_sports,
            contentDescription = "Sports",
            isSelected = category == Category.SPORTS,
            buttonClick = {
                category = Category.SPORTS
                onCategoryClicked(category)
            }
        )
    }
}

@Composable
private fun RowScope.CategoryButton(category: Category, resourceId: Int, contentDescription: String, isSelected: Boolean, buttonClick: (Category) -> Unit) {
    BottomNavigationItem(
        icon = {
            val image = painterResource(id = resourceId)
            Icon(image, contentDescription)
        },
        selectedContentColor = Color.White,
        unselectedContentColor = Color.White.copy(alpha = 0.2f),
        onClick = {
            buttonClick(category)
        },
        selected = isSelected
    )
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