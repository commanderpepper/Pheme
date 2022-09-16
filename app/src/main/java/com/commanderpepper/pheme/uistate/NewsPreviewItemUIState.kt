package com.commanderpepper.pheme.uistate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class NewsPreviewItemUIState(
    val publisher: String,
    val author: String,
    val title: String,
    val preview: String,
    val thumbnail: String,
    val id: Long
)

@Composable
fun NewsPreviewItem(newsPreviewItemUIState: NewsPreviewItemUIState, onClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick(newsPreviewItemUIState.id) }
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (newsPreviewItemUIState.thumbnail.isNotBlank()) {
                AsyncImage(
                    modifier = Modifier.weight(3 / 10f).padding(4.dp),
                    model = newsPreviewItemUIState.thumbnail,
                    contentDescription = null
                )
            }
            Column(modifier = Modifier.weight(7 / 10f)) {
                Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.title)
                if (newsPreviewItemUIState.author.isNotBlank()) {
                    Text(modifier = Modifier.fillMaxWidth(), maxLines = 2, text = newsPreviewItemUIState.author)
                }
                Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.publisher)
//            Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.preview)
            }
        }
    }
}