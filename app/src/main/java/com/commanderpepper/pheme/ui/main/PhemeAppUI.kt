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

@Composable
fun HomeFloatingActionButton(modifier: Modifier = Modifier, lazyListState: LazyListState) {
    val isVisible by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } }
    val job = rememberCoroutineScope()

    AnimatedVisibility(
        modifier = modifier,
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