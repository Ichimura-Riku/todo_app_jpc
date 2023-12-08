package com.example.todoAppJpc.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoAppJpc.R
import com.example.todoAppJpc.ui.components.DatePickerComponent
import com.example.todoAppJpc.ui.components.TimePickerComponent

@Composable
fun TodoEntryBody(
    viewModel: TodoEntryViewModel = hiltViewModel(),
    onTodoValueChange: (TodoState) -> Unit = viewModel::updateTodoState,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val showDatePickerMutableState = remember { mutableStateOf(false) }
    var showDatePickerState by showDatePickerMutableState
    val showTimePickerMutableState = remember { mutableStateOf(false) }
    var showTimePickerState by showTimePickerMutableState
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {

            TodoInputForm(
                viewModel = viewModel,
                onValueChange = onTodoValueChange,
                modifier = Modifier.fillMaxWidth(),
                showDatePickerMutableState = showDatePickerMutableState,
                showTimePickerMutableState = showTimePickerMutableState,
            )

            Button(
                onClick = onSaveClick,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.add_action))
            }
        }
        BottomAppBar(
            modifier = Modifier.statusBarsPadding(),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                IconButton(onClick = { viewModel.setShowContentTextField(!viewModel.getShowContentTextField()) }) {
                    Icon(
                        painterResource(id = if (viewModel.getShowContentTextField()) R.drawable.baseline_edit_note_24 else R.drawable.baseline_notes_24),
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = { showDatePickerState = true }) {
                    Icon(
                        painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = { showDatePickerState = !showDatePickerState }) {
                    Icon(
                        painterResource(id = R.drawable.round_more_horiz_24),
                        contentDescription = "Localized description",
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoInputForm(
    viewModel: TodoEntryViewModel,
    modifier: Modifier = Modifier,
    onValueChange: (TodoState) -> Unit = {},
    showDatePickerMutableState: MutableState<Boolean>,
    showTimePickerMutableState: MutableState<Boolean>,
) {
    val rememberDatePickerState = rememberDatePickerState()
    val deadlineUiViewState by viewModel.deadlineUiViewState.collectAsState()
    val todoState = viewModel.todoUiState.todoState
    val deadlineUiState = viewModel.deadlineUiState
    var showDatePickerState by showDatePickerMutableState
    var showTimePickerState by showTimePickerMutableState
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val isInputDeadlineState =
            deadlineUiState.isInputTimePickerState || deadlineUiState.isInputDatePickerState
        TextField(
            value = todoState.title,
            onValueChange = { onValueChange(todoState.copy(title = it)) },
            label = { Text(stringResource(R.string.todo_title_req)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
        )

        if (viewModel.getShowContentTextField()) {
            TextField(
                value = todoState.content,
                onValueChange = { onValueChange(todoState.copy(content = it)) },
                label = { Text(stringResource(R.string.todo_content_req)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
            )
        }

        if (isInputDeadlineState) {
            InputChip(
                label = { Text(deadlineUiViewState) },
                onClick = { deadlineUiState.setShowDatePicker(!deadlineUiState.showDatePicker) },
                selected = false,
                trailingIcon = {
                    IconButton(onClick = {
                        deadlineUiState.updateIsInputTimePickerState(false)
                        deadlineUiState.updateIsInputDatePickerState(false)
                        deadlineUiState.resetTimePickerState()
                        deadlineUiState.resetDatePickerState()
                    }) {
                        Icon(
                            painterResource(id = R.drawable.round_close_24),
                            contentDescription = "Localized description",
                        )
                    }
                },
            )
        }

        if (showDatePickerState) {
            DatePickerComponent(
                deadlineUiState = deadlineUiState,
                showDatePickerMutableState = showDatePickerMutableState,
                showTimePickerMutableState = showDatePickerMutableState,
                rememberDatePickerState = rememberDatePickerState,
                updateDeadlineUiViewState = { viewModel.updateDeadlineUiViewState() },
            )
        }
        if (showTimePickerState) {
            TimePickerComponent(
                deadlineUiState = deadlineUiState,
                showDatePickerMutableState = showDatePickerMutableState,
                showTimePickerMutableState = showDatePickerMutableState,
                updateDeadlineUiViewState = { viewModel.updateDeadlineUiViewState() },
            )
        }
    }
}
