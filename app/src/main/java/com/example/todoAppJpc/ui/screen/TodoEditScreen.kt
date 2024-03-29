package com.example.todoAppJpc.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.todoAppJpc.R
import com.example.todoAppJpc.ui.components.DatePickerComponent
import com.example.todoAppJpc.ui.components.TimePickerComponent
import com.example.todoAppJpc.ui.navigation.NavigationDestination
import com.example.todoAppJpc.ui.viewmodel.TodoEditViewModel
import com.example.todoAppJpc.ui.viewmodel.TodoState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object TodoEditDestination : NavigationDestination {
    override val route = "todo_edit"
    override val titleRes = R.string.app_edit_page_top_bar
    const val todoIdArg = "TodoId"
    val routeWithArgs = "$route/{$todoIdArg}"
}

// EditScreenの最上位コンポーネント
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier = Modifier.padding(10.dp),
    viewModel: TodoEditViewModel = hiltViewModel(),
    navController: NavController, // rmv = rememberNavController()
    navBackStackEntry: NavBackStackEntry?,
) {
    val coroutineScope = rememberCoroutineScope()
    MaterialTheme {
        Scaffold(
//            topBar = { TodoAppBar(topBarText = stringResource(id = TodoEditDestination.titleRes)) },
            topBar = {
                TodoEditTopAppBar(
                    viewModel = viewModel,
                    navController = navController,
                    coroutineScope = coroutineScope,
                    navBackStackEntry = navBackStackEntry,
                )
            },

            ) { innerPadding ->
            TodoEditBody(
                viewModel = viewModel,
                navController = navController,
                coroutineScope = coroutineScope,
                modifier = modifier.padding(innerPadding),
            )
        }
    }
}

// EditScreenのAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditTopAppBar(
    viewModel: TodoEditViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope,
    navBackStackEntry: NavBackStackEntry?,
) {
    val datePickerViewModel = viewModel.datePickerViewModel
    val timePickerViewModel = viewModel.timePickerViewModel
    val datePickerState = datePickerViewModel.datePickerState.collectAsState()
    val timePickerState = timePickerViewModel.timePickerState.collectAsState()
    val navigationIcon: (@Composable () -> Unit)? =
//        rmv navBackStackEntryはEditcreenか、navGraphで値を持つ
        if (navBackStackEntry?.destination?.route != "main") {
            {
                IconButton(onClick = {
                    navBackEntry(
                        viewModel = viewModel,
                        navController = navController,
                        datePickerState = datePickerState.value,
                        timePickerState = timePickerState.value,

                        )
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
    val eliminateIcon: (@Composable RowScope.() -> Unit) =
        {
            IconButton(onClick = {
                viewModel.setDeleteConfirmationRequired(true)
            }) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "delete")
            }
        }
//    rmv 基本レイアウトはMainBodyScreenと一緒で、
    if (navigationIcon != null) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = TodoEditDestination.titleRes),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            },
            modifier = Modifier.statusBarsPadding(),
            navigationIcon = navigationIcon,
            actions = eliminateIcon,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        )
    }
}


// EditScreenのmainBody
@Composable
fun TodoEditBody(
    viewModel: TodoEditViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        ) {


        TodoEdit(
            viewModel = viewModel,
            todoState = viewModel.todoUiState.todoState,
            onValueChange = viewModel::updateTodoState,

            )

        if (viewModel.getDeleteConfirmationRequired()) {
            EliminateConfirmationDialog(
                onDeleteConfirm = {
                    onClickEliminate(
                        viewModel = viewModel,
                        navController = navController,
                        coroutineScope = coroutineScope,
                    )
                },
                onDeleteCancel = {
                    viewModel.setDeleteConfirmationRequired(false)
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            )
        }
    }
}

// 編集するtodoのTextフィールドなんかがある
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEdit(
    viewModel: TodoEditViewModel,
    todoState: TodoState,
    onValueChange: (TodoState) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val isShowChip = rememberSaveable { mutableStateOf(false) }
    val datePickerViewModel = viewModel.datePickerViewModel
    val timePickerViewModel = viewModel.timePickerViewModel
    val datePickerState = datePickerViewModel.datePickerState.collectAsState()
    val timePickerState = timePickerViewModel.timePickerState.collectAsState()
    val showDatePicker = datePickerViewModel.showDatePicker.collectAsState()
    val showTimePicker = timePickerViewModel.showTimePicker.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
    ) {
        TextField(
            value = todoState.title,
            onValueChange = { onValueChange(todoState.copy(title = it)) },
        )
        Divider()
        TextField(
            value = todoState.content,
            onValueChange = { onValueChange(todoState.copy(content = it)) },
        )

        Divider()
        InputChip(
            label = {
                ChipText(
                    datePickerState = datePickerState.value,
                    timePickerState = timePickerState.value,
                )
            },
            onClick = {
                datePickerViewModel.setShowDatePicker(!showDatePicker.value)
            },
            selected = false,
            trailingIcon = {
                IconButton(onClick = {
                    isShowChip.value = false
                    datePickerViewModel.resetDatePickerState()
                    timePickerViewModel.resetTimePickerState()
                }) {
                    Icon(
                        painterResource(id = R.drawable.round_close_24),
                        contentDescription = "Localized description",
                    )
                }
            },
        )

        if (showDatePicker.value) {
            DatePickerComponent(
                datePickerViewModel = datePickerViewModel,
                timePickerViewModel = timePickerViewModel,
                setChipView = { isShowChip.value = true },
                modifier = modifier,
            )
        }
        if (showTimePicker.value) {
            TimePickerComponent(
                datePickerViewModel = datePickerViewModel,
                timePickerViewModel = timePickerViewModel,
                setChipView = { isShowChip.value = true },

                )
        }
    }
}


// 削除をする時のダイアログ
@Composable
private fun EliminateConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        },
    )
}


// MainScreenに戻るバックキーの処理
@OptIn(ExperimentalMaterial3Api::class)
fun navBackEntry(
    viewModel: TodoEditViewModel,
    navController: NavController,
    datePickerState: DatePickerState,
    timePickerState: TimePickerState,
) {
    viewModel.adventTodo(datePickerState, timePickerState)

    navController.popBackStack()
}

// 削除ボタンを押した時の処理
// todo: ViewModelScopeにするべきでは？
fun onClickEliminate(
    viewModel: TodoEditViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope,
) {
    viewModel.setDeleteConfirmationRequired(false)
    coroutineScope.launch {
        viewModel.eliminateTodo()
    }
    navController.popBackStack()
}
