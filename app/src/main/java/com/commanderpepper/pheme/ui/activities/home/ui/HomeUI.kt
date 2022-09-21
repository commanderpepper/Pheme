package com.commanderpepper.pheme.ui.activities.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.repository.local.Category
import com.commanderpepper.pheme.ui.activities.home.HomeUIState
import com.commanderpepper.pheme.ui.activities.home.HomeViewModel
import com.commanderpepper.pheme.uistate.NewsPreviewItem

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
fun DisplayHomeActivity(viewModel: HomeViewModel, onArticleClicked: (Long) -> Unit, onCategoryClicked: (Category) -> Unit){
    Scaffold(
        bottomBar = { val color = MaterialTheme.colors.primaryVariant
            HomeBottomBar(backgroundColor = color, onCategoryClicked) }
    ) { paddingValues ->
        val homeUIState : HomeUIState by viewModel.homeUIState.collectAsState()
        if(homeUIState.isError){
            DisplayError()
        }
        else if(homeUIState.isLoading){
            LoadingArticles()
        }
        else if(homeUIState.newsPreviewList.isNotEmpty()){
            LazyColumn(modifier = Modifier.padding(paddingValues = paddingValues )) {
                items(homeUIState.newsPreviewList, itemContent = {
                        item -> NewsPreviewItem(item, onClick = onArticleClicked)
                })
            }
        }
    }
}

@Composable
fun LoadingArticles(){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun DisplayError(){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        val image = painterResource(id = R.drawable.pheme_portrait_)
        Image(modifier = Modifier.clip(CircleShape), painter = image, contentDescription = "Error Image")
        Text(text = stringResource(id = R.string.error_message))
    }
}