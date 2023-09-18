package com.example.todo_app_jpc.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_app_jpc.R
import com.example.todo_app_jpc.data.TodoEntity
import com.example.todo_app_jpc.ui.TodoAppViewModelProvider
import com.example.todo_app_jpc.ui.navigation.NavigationDestination
import com.example.todo_app_jpc.ui.todo.TodoEntryBody
import com.example.todo_app_jpc.ui.todo.TodoEntryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


object HomeDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppView(modifier: Modifier = Modifier, viewModel: MainBodyViewModel = viewModel(factory = TodoAppViewModelProvider.Factory)) {
    val topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    )
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
//    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {

            TodoAppBar(
                topBarText = stringResource(id = HomeDestination.titleRes),
                colors = topAppBarColors
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
                        onClick = { viewModel.setShowBottomSheet(true) },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(Icons.Rounded.Add, "localized description")
                    }
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
    { innerPadding ->
        MainScreen(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            sheetState = sheetState,
            scope = scope,
            mainBodyViewModel = viewModel
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    scope: CoroutineScope = rememberCoroutineScope(),
    mainBodyViewModel: MainBodyViewModel
) {
    val todoEntryViewModel: TodoEntryViewModel =
        viewModel(factory = TodoAppViewModelProvider.Factory)
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier, color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (mainBodyViewModel.getShowBottomSheet()) {
                ModalBottomSheet(
                    onDismissRequest = {
                        mainBodyViewModel.setShowBottomSheet(false)
                    },
                    sheetState = sheetState
                ) {
                    // Sheet content
                    TodoEntryBody(
                        todoState = todoEntryViewModel.todoUiState.todoState,
                        onTodoValueChange = todoEntryViewModel::updateTodoState,
                        onSaveClick = {
                            // Note: If the user rotates the screen very fast, the operation may get cancelled
                            // and the item may not be saved in the Database. This is because when config
                            // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                            // be cancelled - since the scope is bound to composition.
                            coroutineScope.launch {
                                todoEntryViewModel.adventTodo()
//                                    Todo: ぱっと見、バックアクションのナビゲーションに関する処理だから一旦放置
//                                    navigateBack()
                            }
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    mainBodyViewModel.setShowBottomSheet(false)
                                }
                            }

                        },
                    )


                }
            }

        }

    }
//    Todoリストの表示のはず。今はクリックしたときも、modifierも、viewModelのデータも読んできてない
    MainBody(
        viewModel = mainBodyViewModel
    )
}

@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    onTodoClick: (Int) -> Unit = {},
    viewModel: MainBodyViewModel = viewModel(factory = TodoAppViewModelProvider.Factory)
) {
    val mainBodyUiState by viewModel.mainBodyUiState.collectAsState()
    val todoEntityList = mainBodyUiState.todoList
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (todoEntityList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            AllTodoList(
                todoEntityList = todoEntityList,
                onTodoClick = { onTodoClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

//　これもtutorialのやつ
@Composable
private fun AllTodoList(
    todoEntityList: List<TodoEntity>,
    onTodoClick: (TodoEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = todoEntityList, key = { it.id }) { item ->
            TodoItem(todoEntity = item, modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .clickable { onTodoClick(item) })
        }
    }
}

@Composable
private fun TodoItem(
    todoEntity: TodoEntity, modifier: Modifier = Modifier
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
                    text = todoEntity.title,
                    style = MaterialTheme.typography.titleLarge,
                )

            }
        }
    }
}
