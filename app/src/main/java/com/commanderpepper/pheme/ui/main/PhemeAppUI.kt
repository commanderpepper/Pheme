package com.commanderpepper.pheme.ui.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.commanderpepper.pheme.ui.homebottombar.HomeBottomBar
import com.commanderpepper.pheme.ui.hometopbar.HomeTopBar
import com.commanderpepper.pheme.ui.screens.articlelist.Articles

@Composable
fun PhemeAppUI(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    lazyListState: LazyListState,
    onArticleClicked: (Long) -> Unit
) {
    mainViewModel.loadData()

    Scaffold(modifier = modifier,
        topBar = {
            val color = MaterialTheme.colors.primaryVariant
            HomeTopBar(
                modifier = Modifier.fillMaxWidth(),
                color = color,
                onClearSearch = {
                    mainViewModel.clearSearch()
                },
                onTextChange = {
                    mainViewModel.searchArticles(it)
                },
                textFlow = mainViewModel.searchQueryFlow
            )
        },
        bottomBar = {
            val color = MaterialTheme.colors.primaryVariant
            HomeBottomBar(
                categoryUIStateFlow = mainViewModel.categoryUIState,
                backgroundColor = color,
                onCategoryClicked = mainViewModel::categoryClicked
            )
        }) { paddingValues ->
        Articles(
            modifier = Modifier.padding(paddingValues),
            mainViewModel.articleUIListStateFlow,
            lazyListState = lazyListState,
            onArticleClicked = onArticleClicked
        )
    }
}