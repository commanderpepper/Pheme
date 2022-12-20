package com.commanderpepper.pheme.ui.uistate

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.commanderpepper.pheme.ui.util.Loading
import dev.romainguy.text.combobreaker.material3.TextFlow

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
    Column(modifier = modifier
        .fillMaxSize()
        .padding(4.dp)) {
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
        Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, text = newsItemUIState.title)
        Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.bodyMedium, text = newsItemUIState.author)
        Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.bodyMedium, text = newsItemUIState.publisher)
        Text(modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.bodyMedium, text = newsItemUIState.date)
        Text(modifier = Modifier
            .weight(7 / 10f)
            .fillMaxWidth()
            .verticalScroll(scrollState),
            text = newsItemUIState.content,
            style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun NewsItemExpanded(modifier: Modifier = Modifier, newsItemUIState: NewsItemUIState) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = MaterialTheme.typography.titleLarge.toSpanStyle()) {
            append(newsItemUIState.title)
        }
    }

    Column(modifier = modifier.verticalScroll(ScrollState(0))) {
        TextFlow(
            text = annotatedString,
            modifier = Modifier.fillMaxSize()
        ) {
            if (newsItemUIState.thumbnail.isNotBlank()) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .semantics { contentDescription = NEWS_ITEM_IMAGE_CONTENT_DESCRIPTION }
                        .align(Alignment.TopStart)
                        .widthIn(160.dp, 320.dp)
                        .padding(8.dp),
                    model = newsItemUIState.thumbnail,
                    loading = { Loading() },
                    contentDescription = null
                )
            }
        }
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = newsItemUIState.author
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = newsItemUIState.publisher
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = newsItemUIState.date
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = newsItemUIState.content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemPreview(){
    NewsItem(newsItemUIState = NewsItemUIState(
        publisher = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
        author = "Etiam vitae arcu ut metus iaculis interdum",
        title = "Sed nec enim at nunc tempor placerat.",
        thumbnail = "https://i.imgur.com/olisBgy.png",
        date = "Jan 01, 2000",
        content = "Cras elementum urna leo, eget vestibulum augue porta vitae. Morbi dui nunc, venenatis a risus suscipit, ullamcorper mattis erat. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque convallis fringilla finibus. Integer quis condimentum elit, tempus faucibus nulla. In iaculis pharetra tincidunt. Maecenas aliquam ex sagittis rhoncus blandit. Fusce egestas nisl eget libero aliquet, vel ornare enim congue. Donec sed porttitor est, a pretium mi. Cras varius mi sed purus venenatis efficitur. Sed luctus eleifend odio a malesuada. Etiam feugiat libero et maximus fermentum."
    )
    )
}

@Preview(showBackground = true, widthDp = 900, heightDp = 600)
@Composable
fun NewsItemExpandedPreview(){
    NewsItemExpanded(newsItemUIState = NewsItemUIState(
        publisher = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
        author = "Etiam vitae arcu ut metus iaculis interdum",
        title = "Sed nec enim at nunc tempor placerat.",
        thumbnail = "https://i.imgur.com/olisBgy.png",
        date = "Jan 01, 2000",
        content = "Cras elementum urna leo, eget vestibulum augue porta vitae. Morbi dui nunc, venenatis a risus suscipit, ullamcorper mattis erat. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque convallis fringilla finibus. Integer quis condimentum elit, tempus faucibus nulla. In iaculis pharetra tincidunt. Maecenas aliquam ex sagittis rhoncus blandit. Fusce egestas nisl eget libero aliquet, vel ornare enim congue. Donec sed porttitor est, a pretium mi. Cras varius mi sed purus venenatis efficitur. Sed luctus eleifend odio a malesuada. Etiam feugiat libero et maximus fermentum."
    )
    )
}