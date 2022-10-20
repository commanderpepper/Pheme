package com.commanderpepper.pheme.ui.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
    )
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.4f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 100f, translateAnimation + 100f),
        tileMode = TileMode.Mirror,
    )
    return@composed this.then(background(brush, shape))
}

@Composable
fun LoadingNewsPreviewItem(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = modifier
                .weight(3 / 10f)
                .height(64.dp)
                .padding(8.dp)
                .shimmerBackground()
        )
        Column(modifier = Modifier.weight(7 / 10f)) {
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
                    .padding(4.dp)
                    .shimmerBackground()
            )
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
                    .padding(4.dp)
                    .shimmerBackground()
            )
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
                    .padding(4.dp)
                    .shimmerBackground()
            )
        }
    }

}

@Composable
fun LoadingArticles() {
    Column(modifier = Modifier.fillMaxHeight()) {
        repeat(10) {
            LoadingNewsPreviewItem()
        }
    }
}

@Composable
fun LoadingArticle() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .padding(8.dp)
            .weight(3 / 10f)
            .fillMaxWidth()
            .shimmerBackground())
        Box(modifier = Modifier
            .padding(PaddingValues(horizontal = 8.dp, vertical = 4.dp))
            .height(16.dp)
            .fillMaxWidth()
            .shimmerBackground())
        Box(modifier = Modifier
            .padding(PaddingValues(horizontal = 8.dp, vertical = 4.dp))
            .height(16.dp)
            .fillMaxWidth()
            .shimmerBackground())
        Box(modifier = Modifier
            .padding(PaddingValues(horizontal = 8.dp, vertical = 4.dp))
            .height(16.dp)
            .fillMaxWidth()
            .shimmerBackground())
        Box(modifier = Modifier
            .padding(PaddingValues(horizontal = 8.dp, vertical = 4.dp))
            .height(16.dp)
            .fillMaxWidth()
            .shimmerBackground())
        Box(modifier = Modifier
            .padding(PaddingValues(horizontal = 8.dp, vertical = 4.dp))
            .weight(7 / 10f)
            .fillMaxWidth()
            .shimmerBackground())
    }
}

@Composable
fun LoadingArticleExpanded() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Row(modifier = Modifier.weight(4 / 10f).fillMaxWidth()) {
            Box(modifier = Modifier
                .padding(8.dp)
                .weight(7 / 20f)
                .fillMaxWidth()
                .fillMaxHeight()
                .shimmerBackground())
            Box(modifier = Modifier
                .padding(8.dp)
                .weight(13 / 20f)
                .fillMaxWidth()
                .fillMaxHeight()
                .shimmerBackground())
        }
        Box(modifier = Modifier
            .padding(PaddingValues(horizontal = 8.dp, vertical = 4.dp))
            .weight(6 / 10f)
            .fillMaxWidth()
            .shimmerBackground())
    }
}

@Preview
@Composable
fun PreviewLoadingArticles() {
    LoadingArticleExpanded()
}