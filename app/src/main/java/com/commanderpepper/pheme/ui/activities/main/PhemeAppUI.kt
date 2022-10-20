package com.commanderpepper.pheme.ui.activities.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.commanderpepper.pheme.ui.homebottombar.HomeBottomBar
import com.commanderpepper.pheme.ui.screens.articlelist.Articles

@Composable
fun PhemeAppUI(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    onArticleClicked: (Long) -> Unit
) {
    val onCategoryClicked = mainViewModel::categoryClicked
    mainViewModel.loadArticles()

    Scaffold(modifier = modifier,
        bottomBar = {
            val color = MaterialTheme.colors.primaryVariant
            HomeBottomBar(
                categoryUIStateFlow = mainViewModel.categoryUIState,
                backgroundColor = color,
                onCategoryClicked = onCategoryClicked
            )
        }) { paddingValues ->
        Articles(
            modifier = Modifier.padding(paddingValues),
            mainViewModel.articleUIListStateFlow,
            onArticleClicked = onArticleClicked
        )
    }
}