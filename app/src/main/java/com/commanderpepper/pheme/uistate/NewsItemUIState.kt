package com.commanderpepper.pheme.uistate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.commanderpepper.pheme.ui.util.Loading

data class NewsItemUIState(
    val publisher: String,
    val author: String,
    val title: String,
    val thumbnail: String,
    val date: String,
    val content: String
)

const val NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION = "Article Thumbnail"

@Composable
fun NewsItem(modifier: Modifier = Modifier, newsItemUIState: NewsItemUIState){
    val scrollState = rememberScrollState(0)
    Box(modifier = modifier
        .fillMaxSize()
        .padding(4.dp)) {
        Column {
            if (newsItemUIState.thumbnail.isNotBlank()) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .semantics { contentDescription = NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION }
                        .weight(3 / 10f)
                        .fillMaxWidth(),
                    model = newsItemUIState.thumbnail,
                    loading = { Loading() },
                    contentDescription = null
                )
            }
            Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.h5, text = newsItemUIState.title)
            Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.subtitle2, text = newsItemUIState.author)
            Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.subtitle2, text = newsItemUIState.publisher)
            Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.subtitle2, text = newsItemUIState.date)
            Text(modifier = Modifier
                .weight(7 / 10f)
                .fillMaxWidth()
                .verticalScroll(scrollState), text = newsItemUIState.content)
        }
    }
}

@Composable
fun NewsItemExpanded(modifier: Modifier = Modifier, newsItemUIState: NewsItemUIState){
    val scrollState = rememberScrollState(0)
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier
//            .weight(4 / 10f)
            .fillMaxHeight(4/10f)
            .padding(4.dp)) {
            if(newsItemUIState.thumbnail.isNotBlank()){
                SubcomposeAsyncImage(
                    modifier = Modifier.semantics { contentDescription = NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION },
                    model = newsItemUIState.thumbnail,
                    loading = { Loading() },
                    contentDescription = null
                )
            }
            Column() {
                Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.h5, text = newsItemUIState.title)
                Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.subtitle2, text = newsItemUIState.author)
                Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.subtitle2, text = newsItemUIState.publisher)
                Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.subtitle2, text = newsItemUIState.date)
            }
        }
        Text(modifier = Modifier
//            .weight(6 / 10f)
            .fillMaxHeight()
            .verticalScroll(scrollState), text = newsItemUIState.content)
    }
}