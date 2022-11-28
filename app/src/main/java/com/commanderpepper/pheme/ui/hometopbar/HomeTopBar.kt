package com.commanderpepper.pheme.ui.hometopbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    actionIcon: ImageVector,
    actionIconContentDescription: String?,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    navigationText: StateFlow<String>,
    categoryText: StateFlow<String>,
    onTextChange: (String) -> Unit,
    onAction: () -> Unit
){
    val searchText = navigationText.collectAsState()
    val category = categoryText.collectAsState()

    var isSearchTextVisible by remember {
        mutableStateOf(false)
    }
    val isCategoryTextVisible by remember {
        mutableStateOf(isSearchTextVisible.not())
    }

    CenterAlignedTopAppBar(
        title = {
            if(isCategoryTextVisible){
                Text(text = category.value)
            }
            AnimatedVisibility(visible = isSearchTextVisible){
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchText.value,
                    onValueChange = onTextChange,
                    textStyle = MaterialTheme.typography.titleMedium,
                    placeholder = {
                        Text(text = "Search")
                    }
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                if(isSearchTextVisible){
                    onAction()
                }
                isSearchTextVisible = isSearchTextVisible.not()
            }) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            AnimatedVisibility(visible = isSearchTextVisible) {
                IconButton(onClick = { onAction() }) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier
    )
}