package com.example.todo_app_jpc.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_app_jpc.Data.Todo
import com.example.todo_app_jpc.ItemData.Item
import com.example.todo_app_jpc.R
import com.example.todo_app_jpc.ui.AppViewModelProvider
import com.example.todo_app_jpc.ui.item.formatedPrice
import com.example.todo_app_jpc.ui.theme.Todo_app_jpcTheme
import com.example.todo_app_jpc.ui.todo.TodoEntryBody
import com.example.todo_app_jpc.ui.todo.TodoEntryViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier,

        ) {
        Text(
            text = "Hello $name!",
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer

        )
        TextField(value = text, onValueChange = { text = it })

    }
}

@Composable
private fun MainScreen(
    todoList: List<Todo>, onTodoClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (todoList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            AllTodoList(
                todoList = todoList,
                onTodoClick = { onTodoClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun AllTodoList(
    todoList: List<Todo>, onTodoClick: (Todo) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier){
        items(items = todoList, key = {it.id}) { item ->
            TodoItem(todo = item, modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .clickable { onTodoClick(item) })
        }
    }
}

@Composable
private fun TodoItem(
    todo: Todo, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
//            rowいらない説濃厚
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleLarge,
                )

            }
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppView() {
    val topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    )
    Todo_app_jpcTheme {
        Scaffold(
            topBar = {

                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = {
                        Text(
                            text = "top app bar",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    colors = topAppBarColors,
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.statusBarsPadding()
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Row {
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    painterResource(id = R.drawable.round_format_list_bulleted_24),
                                    contentDescription = "Localized description"
                                )
                            }
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    painterResource(id = R.drawable.round_swap_vert_24),
                                    contentDescription = "Localized description"
                                )
                            }
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    painterResource(id = R.drawable.round_more_horiz_24),
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                        FloatingActionButton(
                            onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(Icons.Rounded.Add, "localized description")
                        }
                    }
                }
            },
            content = {

                val viewModel: TodoEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
                val coroutineScope = rememberCoroutineScope()
                Surface(
                    modifier = Modifier, color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TodoEntryBody(
                            todoUiState = viewModel.todoUiState,
                            onTodoValueChange = viewModel::updateUiState,
                            onSaveClick = {
                                // Note: If the user rotates the screen very fast, the operation may get cancelled
                                // and the item may not be saved in the Database. This is because when config
                                // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                                // be cancelled - since the scope is bound to composition.
                                coroutineScope.launch {
                                    viewModel.saveTodo()
//                                    Todo: ぱっと見、バックアクションのナビゲーションに関する処理だから一旦放置
//                                    navigateBack()
                                }
                            },
                        )
                    }
                }
            },
//            content = {
//                val viewModel: TodoEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
//                val coroutineScope = rememberCoroutineScope()
//                val todoUiState = viewModel.todoUiState,
//                Surface(
//                    modifier = Modifier, color = MaterialTheme.colorScheme.background
//                ) {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
////                        Greeting("Android")
//                        MainScreen(todoList = viewModel.MainBodyUiState.collectAsState(), onTodoClick = )
//                    }
//                }
//            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ScfPreview() {
    Todo_app_jpcTheme {
        MyAppView()
    }
}

