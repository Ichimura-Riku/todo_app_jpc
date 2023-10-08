package com.example.todoAppJpc.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.todoAppJpc.R
import com.example.todoAppJpc.ui.navigation.NavigationDestination

object TodoDetailDestination : NavigationDestination {
    override val route = "todo_details"
    override val titleRes = R.string.app_detail_page_top_bar
    const val todoIdArg = "TodoId"
    val routeWithArgs = "$route/{$todoIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier.padding(10.dp),
    viewModel: TodoDetailViewModel = hiltViewModel(),
    navController: NavController, // rmv = rememberNavController()
    navBackStackEntry: NavBackStackEntry?,
) {
    MaterialTheme {
        Scaffold(
//            topBar = { TodoAppBar(topBarText = stringResource(id = TodoDetailDestination.titleRes)) },
            topBar = {
                TodoDetailTopAppBar(
                    navController = navController,
                    navBackStackEntry = navBackStackEntry,
                )
            },

            ) { innerPadding ->
            TodoDetailBody(modifier = modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailTopAppBar(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?,
) {
    val navigationIcon: (@Composable () -> Unit)? =
//        rmv navBackStackEntryはDetailScreenか、navGraphで値を持つ
        if (navBackStackEntry?.destination?.route != "main") {
            {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        } else {
            null
        }
//    rmv 基本レイアウトはMainBodyScreenと一緒で、
    if (navigationIcon != null) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = TodoDetailDestination.titleRes),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            },
            modifier = Modifier.statusBarsPadding(),
            navigationIcon = navigationIcon,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        )
    }
}

@Composable
fun TodoDetailBody(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

    ) {
        TodoDetails()
    }
}

@Composable
fun TodoDetails(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            Text(text = "title")
            TextField(value = "textField", onValueChange = {})
            Divider()
        }
    }
}
