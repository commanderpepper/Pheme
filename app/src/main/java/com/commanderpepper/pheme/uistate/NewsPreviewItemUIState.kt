package com.commanderpepper.pheme.uistate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

data class NewsPreviewItemUIState(
    val publisher: String,
    val author: String,
    val title: String,
    val preview: String,
    val thumbnail: String
)

@Composable
fun NewsPreviewItem(newsPreviewItemUIState: NewsPreviewItemUIState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (newsPreviewItemUIState.thumbnail.isNotBlank()) {
            AsyncImage(
                modifier = Modifier.weight(3 / 10f),
                model = newsPreviewItemUIState.thumbnail,
                contentDescription = null
            )
        }
        Column(modifier = Modifier.weight(7 / 10f)) {
            Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.title)
            if (newsPreviewItemUIState.author.isNotBlank()) {
                Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.author)
            }
            Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.publisher)
//            Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.preview)
        }
    }
}