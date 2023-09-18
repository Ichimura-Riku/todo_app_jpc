package com.example.todo_app_jpc.ui.home

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_app_jpc.ui.theme.Todo_app_jpcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppBar(
    topBarText: String = "top app bar",
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    )
) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            Text(
                text = topBarText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        colors = colors,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ScfPreview() {
    Todo_app_jpcTheme {
        MyAppView()
    }
}

