package com.example.todoAppJpc.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoAppJpc.R
import com.example.todoAppJpc.data.TodoEntity
import com.example.todoAppJpc.ui.navigation.NavigationDestination
import com.example.todoAppJpc.ui.screen.TodoEntryBody
import com.example.todoAppJpc.ui.viewmodel.MainBodyViewModel
import com.example.todoAppJpc.ui.viewmodel.TodoEntryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppView(
    modifier: Modifier = Modifier,
    onTodoClick: (Int) -> Unit = {},
    viewModel: MainBodyViewModel = hiltViewModel(),
) {
    val topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
    )
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
//    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TodoAppBar(
                topBarText = stringResource(id = HomeDestination.titleRes),
                colors = topAppBarColors,
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.statusBarsPadding(),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Box(modifier = modifier)

                    FloatingActionButton(
                        onClick = { viewModel.setShowBottomSheet(true) },
                        modifier = Modifier.padding(10.dp),
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
// //                        Greeting("Android")
//                        MainScreen(todoList = viewModel.MainBodyUiState.collectAsState(), onTodoClick = )
//                    }
//                }
//            }
    ) { innerPadding ->
        MainScreen(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            onTodoClick = onTodoClick,
            sheetState = sheetState,
            scope = scope,
            mainBodyViewModel = viewModel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onTodoClick: (Int) -> Unit,
    sheetState: SheetState,
    scope: CoroutineScope = rememberCoroutineScope(),
    mainBodyViewModel: MainBodyViewModel,
) {
    val todoEntryViewModel: TodoEntryViewModel =
        hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (mainBodyViewModel.getShowBottomSheet()) {
                ModalBottomSheet(
                    onDismissRequest = {
                        mainBodyViewModel.setShowBottomSheet(false)
                    },
                    sheetState = sheetState,
                    contentColor = MaterialTheme.colorScheme.surface,
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    // Sheet content
                    TodoEntryBody(
                        // これもいらない説
                        closeBottomSheet = {
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
        modifier = modifier,
        onTodoClick = onTodoClick,
        viewModel = mainBodyViewModel,
    )
}

@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    onTodoClick: (Int) -> Unit = {},
    viewModel: MainBodyViewModel = hiltViewModel(),
) {
    val mainBodyUiState by viewModel.mainBodyUiState.collectAsState()
    val todoEntityList = mainBodyUiState.todoList
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (todoEntityList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        } else {
            AllTodoList(
                todoEntityList = todoEntityList,
                onTodoClick = { onTodoClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
            )
        }
    }
}

// 　これもtutorialのやつ
@Composable
private fun AllTodoList(
    todoEntityList: List<TodoEntity>,
    onTodoClick: (TodoEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(items = todoEntityList, key = { it.id }) { item ->
            TodoItem(
                todoEntity = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onTodoClick(item) },
            )
        }
    }
}

@Composable
private fun TodoItem(
    todoEntity: TodoEntity,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        ) {
//            rowいらない説濃厚
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = todoEntity.title,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}
