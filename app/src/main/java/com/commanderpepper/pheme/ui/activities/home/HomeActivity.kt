package com.commanderpepper.pheme.ui.activities.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.commanderpepper.pheme.ui.theme.PhemeTheme
import com.commanderpepper.pheme.uistate.NewsPreviewItem
import com.commanderpepper.pheme.uistate.NewsPreviewItemUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val vm: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhemeTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowList(modifier = Modifier.fillMaxSize(), viewModel = vm)
                }
            }
        }
    }
}

@Composable
fun ShowList(modifier: Modifier, viewModel: HomeViewModel){
    val data : List<NewsPreviewItemUIState> by viewModel.flow.collectAsState(initial = emptyList())
    LazyColumn(modifier = modifier) {
        items(data, itemContent = {
            item -> NewsPreviewItem(item)
            Divider(color = Color.Black, thickness = 1.dp)
        })
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhemeTheme {
        NewsPreviewItem(NewsPreviewItemUIState("New York Times",
            "John Doe",
            "The Weather",
            "Its sunny",
            "https://example.com/image.jpg"))
    }
}