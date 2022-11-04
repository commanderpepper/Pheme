package com.commanderpepper.pheme.ui.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
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
        MaterialTheme.colorScheme.outline,
        MaterialTheme.colorScheme.outlineVariant
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
    Card(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .height(96.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = modifier
                    .weight(3 / 10f)
                    .fillMaxHeight()
                    .padding(8.dp)
                    .shimmerBackground(MaterialTheme.shapes.medium)
            )
            Column(
                modifier = Modifier
                    .weight(7 / 10f)
                    .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .shimmerBackground(MaterialTheme.shapes.medium)
                )
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .shimmerBackground(MaterialTheme.shapes.medium)
                )
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .shimmerBackground(MaterialTheme.shapes.medium)
                )
            }
        }
    }
}

@Composable
fun LoadingArticles(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxHeight()) {
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
        Row(modifier = Modifier
            .weight(4 / 10f)
            .fillMaxWidth()) {
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
    LoadingNewsPreviewItem()
}