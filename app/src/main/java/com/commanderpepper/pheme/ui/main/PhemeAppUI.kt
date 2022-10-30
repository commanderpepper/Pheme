package com.commanderpepper.pheme.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.commanderpepper.pheme.R
import com.commanderpepper.pheme.ui.homebottombar.HomeBottomBar
import com.commanderpepper.pheme.ui.hometopbar.HomeTopBar
import com.commanderpepper.pheme.ui.screens.articlelist.Articles
import kotlinx.coroutines.launch

@Composable
fun PhemeAppUI(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    lazyListState: LazyListState,
    onArticleClicked: (Long) -> Unit
) {
    Scaffold(
        modifier = modifier,
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
            val job = rememberCoroutineScope()

            HomeBottomBar(
                categoryUIStateFlow = mainViewModel.categoryUIState,
                backgroundColor = color,
                onCategoryClicked = { category ->
                    mainViewModel.categoryClicked(category)
                    job.launch {
                        lazyListState.scrollToItem(0)
                    }
                })
        },
        floatingActionButton = { HomeFloatingActionButton(lazyListState) },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Articles(
            modifier = Modifier.padding(paddingValues),
            mainViewModel.articleUIListStateFlow,
            lazyListState = lazyListState,
            onArticleClicked = onArticleClicked
        )
    }
}

@Composable
fun HomeFloatingActionButton(lazyListState: LazyListState) {
    val isVisible by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } }
    val job = rememberCoroutineScope()

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandIn { IntSize(width = 1, height = 1) },
        exit = fadeOut() + shrinkOut { IntSize(width = 1, height = 1) }
    ) {
        Button(modifier = Modifier.clip(CircleShape), onClick = {
            job.launch {
                lazyListState.scrollToItem(0)
            }
        }) {
            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Rounded.KeyboardArrowUp,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Scroll to top of list"
            )
            Text( modifier = Modifier.align(Alignment.CenterVertically), text = stringResource(R.string.home_floating_action_button_action_text))
        }
    }
}