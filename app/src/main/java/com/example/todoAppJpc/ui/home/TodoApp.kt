package com.example.todoAppJpc.ui.home

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoAppJpc.R
import com.example.todoAppJpc.ui.navigation.TodoNavHost
import com.example.todoAppJpc.ui.theme.TodoAppJpcTheme

@Composable
fun TodoApp(navController: NavHostController = rememberNavController()){
    TodoNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppBar(
    topBarText: String = stringResource(R.string.app_name),
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



@Preview(showBackground = true)
@Composable
fun ScfPreview() {
    TodoAppJpcTheme {
        MyAppView(onTodoClick = {})
    }
}

