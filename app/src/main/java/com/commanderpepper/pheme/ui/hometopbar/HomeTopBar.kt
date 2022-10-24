package com.commanderpepper.pheme.ui.hometopbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.commanderpepper.pheme.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    color: Color,
    textFlow: StateFlow<String>,
    onTextChange: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    TopAppBar(modifier = modifier.fillMaxWidth(), backgroundColor = color) {
        val text = textFlow.collectAsState()
        // Focus Manager is used to dismiss the keyboard when the user clicks on the clear icon
        val focusManager = LocalFocusManager.current

        TextField(
            modifier = modifier.padding(2.dp),
            value = text.value,
            placeholder = {
                Text(text = stringResource(id = R.string.home_activity_top_bar_search_placeholder_text))
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                onTextChange(it)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colors.onBackground,
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
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = "Clear Search Icon"
                    )
                }
            })
    }
}