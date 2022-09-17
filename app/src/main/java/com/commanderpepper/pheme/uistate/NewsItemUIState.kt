package com.commanderpepper.pheme.uistate

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class NewsItemUIState(
    val publisher: String,
    val author: String,
    val title: String,
    val thumbnail: String,
    val content: String
)

@Composable
fun NewsItem(newsItemUIState: NewsItemUIState){
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            if (newsItemUIState.thumbnail.isNotBlank()) {
                AsyncImage(
                    modifier = Modifier.weight(3 / 10f).padding(4.dp),
                    model = newsItemUIState.thumbnail,
                    contentDescription = null
                )
            }
            Text(modifier = Modifier.fillMaxWidth(), text = newsItemUIState.title)
            Text(modifier = Modifier.fillMaxWidth(), text = newsItemUIState.author)
            Text(modifier = Modifier.fillMaxWidth(), text = newsItemUIState.publisher)
            Text(modifier = Modifier.fillMaxWidth(), text = newsItemUIState.content)
        }
    }
}