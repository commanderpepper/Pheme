package com.commanderpepper.pheme.uistate

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun NewsItem(modifier: Modifier = Modifier, newsItemUIState: NewsItemUIState){
    Box(modifier = modifier
        .fillMaxSize()
        .padding(4.dp)) {
        Column {
            if (newsItemUIState.thumbnail.isNotBlank()) {
                SubcomposeAsyncImage(
                    modifier = Modifier
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
                .fillMaxWidth(), text = newsItemUIState.content)
        }
    }
}

@Composable
fun NewsItemExpanded(modifier: Modifier = Modifier, newsItemUIState: NewsItemUIState){
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = modifier.weight(4 / 10f).padding(4.dp)) {
            if(newsItemUIState.thumbnail.isNotBlank()){
                SubcomposeAsyncImage(
                    modifier = Modifier,
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
        Text(modifier = Modifier.weight(6 / 10f).fillMaxHeight(), text = newsItemUIState.content)
    }
}