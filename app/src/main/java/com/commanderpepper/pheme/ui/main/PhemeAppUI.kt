package com.commanderpepper.pheme.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.commanderpepper.pheme.R
import kotlinx.coroutines.launch

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PhemeAppUI(
//    modifier: Modifier = Modifier,
//    mainViewModel: MainViewModel = hiltViewModel(),
//    lazyListState: LazyListState,
//    onArticleClicked: (Long) -> Unit
//) {
//    Scaffold(
//        modifier = Modifier,
//        topBar = {
//            HomeTopBar(
//                navigationIcon = Icons.Rounded.Search,
//                navigationIconContentDescription = null,
//                actionIcon = Icons.Rounded.Clear,
//                actionIconContentDescription = null,
//                navigationText = mainViewModel.searchQueryFlow,
//                categoryText = mainViewModel.homeTopAppBarCategoryTextFlow,
//                onTextChange = mainViewModel::searchArticles,
//                onAction = mainViewModel::clearSearch
//            )
//        },
//        bottomBar = {
//            val color = MaterialTheme.colorScheme.primary
//            val job = rememberCoroutineScope()
//
//            HomeBottomBar(
//                categoryUIStateFlow = mainViewModel.categoryUIState,
//                backgroundColor = color,
//                onCategoryClicked = { category ->
//                    mainViewModel.categoryClicked(category)
//                    job.launch {
//                        lazyListState.scrollToItem(0)
//                    }
//                })
//        },
//        floatingActionButton = { HomeFloatingActionButton(lazyListState) },
//        floatingActionButtonPosition = FabPosition.End
//    ) { paddingValues ->
//        Articles(
//            modifier = Modifier.padding(paddingValues),
//            mainViewModel.articleUIListStateFlow,
//            lazyListState = lazyListState,
//            onArticleClicked = onArticleClicked
//        )
//    }
//}

@Composable
fun HomeFloatingActionButton(lazyListState: LazyListState) {
    val isVisible by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } }
    val job = rememberCoroutineScope()

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandIn { IntSize(width = 1, height = 1) },
        exit = fadeOut() + shrinkOut { IntSize(width = 1, height = 1) }
    ) {
        Button(modifier = Modifier, onClick = {
            job.launch {
                lazyListState.scrollToItem(0)
            }
        }) {
            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Rounded.KeyboardArrowUp,
                contentDescription = "Scroll to top of list"
            )
            Text(modifier = Modifier.align(Alignment.CenterVertically), text = stringResource(R.string.home_floating_action_button_action_text))
        }
    }
}