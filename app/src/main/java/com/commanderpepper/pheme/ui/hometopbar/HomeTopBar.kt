package com.commanderpepper.pheme.ui.hometopbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    color: Color,
    textFlow: StateFlow<String>,
    onTextChange: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    val text = textFlow.collectAsState()
    // Focus Manager is used to dismiss the keyboard when the user clicks on the clear icon
    val focusManager = LocalFocusManager.current

    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { TextField(
            value = text.value,
            placeholder = {
              Text(text = "Search")
            },
            onValueChange = {onTextChange(it)},
            maxLines = 1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    onClearSearch()
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colorScheme.surfaceTint,
                        contentDescription = "Clear Search Icon"
                    )
                }
            }
        )
    })
}