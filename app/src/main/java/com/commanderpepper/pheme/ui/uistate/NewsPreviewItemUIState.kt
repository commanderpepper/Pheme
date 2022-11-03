package com.commanderpepper.pheme.ui.uistate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

/**
 * Check article for title, author and publisher
 * @param searchQuery - text to query the NewsPreviewItemUIState against
 */
fun NewsPreviewItemUIState.checkArticle(searchQuery: String): Boolean {
    return this.title.contains(searchQuery, ignoreCase = true) ||
            this.author.contains(searchQuery, ignoreCase = true) ||
            this.publisher.contains(searchQuery, ignoreCase = true)
}

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
                        .padding(horizontal = 4.dp)
                        .clip(MaterialTheme.shapes.medium),
                    model = newsPreviewItemUIState.thumbnail,
                    loading = { Loading() },
                    contentDescription = null
                )
            }
            Column(modifier = Modifier.weight(7 / 10f)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = newsPreviewItemUIState.title,
                    style = MaterialTheme.typography.titleMedium
                )
                if (newsPreviewItemUIState.author.isNotBlank()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        text = newsPreviewItemUIState.author,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = newsPreviewItemUIState.publisher,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}