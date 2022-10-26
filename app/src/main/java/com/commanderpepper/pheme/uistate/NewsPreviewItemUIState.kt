package com.commanderpepper.pheme.uistate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.commanderpepper.pheme.ui.util.Loading

data class NewsPreviewItemUIState(
    val publisher: String,
    val author: String,
    val title: String,
    val thumbnail: String,
    val id: Long
)

const val NEWS_PREVIEW_IMAGE_CONTENT_DESCRIPTION = "Article Thumbnail"

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
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .semantics {
                            contentDescription = NEWS_PREVIEW_IMAGE_CONTENT_DESCRIPTION
                        }
                        .weight(3 / 10f)
                        .padding(4.dp),
                    model = newsPreviewItemUIState.thumbnail,
                    loading = { Loading() },
                    contentDescription = null
                )
            }
            Column(modifier = Modifier.weight(7 / 10f)) {
                Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.title)
                if (newsPreviewItemUIState.author.isNotBlank()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        text = newsPreviewItemUIState.author
                    )
                }
                Text(modifier = Modifier.fillMaxWidth(), text = newsPreviewItemUIState.publisher)
            }
        }
    }
}