package com.example.todoAppJpc.ui.todo

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object TodoEditDestination : NavigationDestination {
    override val route = "todo_edit"
    override val titleRes = R.string.app_edit_page_top_bar
    const val todoIdArg = "TodoId"
    val routeWithArgs = "$route/{$todoIdArg}"
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditTopAppBar(
    viewModel: TodoEditViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope,
    navBackStackEntry: NavBackStackEntry?,
) {
    val navigationIcon: (@Composable () -> Unit)? =
//        rmv navBackStackEntryはEditcreenか、navGraphで値を持つ
        if (navBackStackEntry?.destination?.route != "main") {
            {
                IconButton(onClick = {
                    navBackEntry(
                        viewModel = viewModel,
                        navController = navController,
                        coroutineScope = coroutineScope,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEdit(
    viewModel: TodoEditViewModel,
    todoState: TodoState,
    onValueChange: (TodoState) -> Unit = {},
    modifier: Modifier = Modifier,
) {
//    val deadlineUiViewState by viewModel.deadlineUiViewState.collectAsState()
//    val isInputDeadlineState =
//        viewModel.isInputTimePickerState || viewModel.isInputDatePickerState
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
        TextField(
            value = "${todoState.deadlineTimeMinute}",
            onValueChange = { onValueChange(todoState.copy(content = it)) },
        )
        Divider()
//        InputChip(
//            label = { Text(deadlineUiViewState) },
//            onClick = { viewModel.setShowDatePicker(!viewModel.getShowDatePicker()) },
//            selected = false,
//            trailingIcon = {
//                IconButton(onClick = {
//                    viewModel.updateIsInputTimePickerState(false)
//                    viewModel.updateIsInputDatePickerState(false)
//                    viewModel.resetTimePickerState()
//                    viewModel.resetDatePickerState()
//                }) {
//                    Icon(
//                        painterResource(id = R.drawable.round_close_24),
//                        contentDescription = "Localized description",
//                    )
//                }
//            },
//        )
//
//        if (viewModel.getShowDatePicker()) {
//            DatePickerComponent(
//                viewModel = viewModel,
//                rememberDatePickerState = rememberDatePickerState,
//            )
//        }
//        if (viewModel.getShowTimePicker()) {
//            TimePickerComponent(viewModel = viewModel)
//        }

    }
}

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

fun navBackEntry(
    viewModel: TodoEditViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope,
) {
    coroutineScope.launch {
        viewModel.adventTodo()
    }
    navController.popBackStack()
}

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
