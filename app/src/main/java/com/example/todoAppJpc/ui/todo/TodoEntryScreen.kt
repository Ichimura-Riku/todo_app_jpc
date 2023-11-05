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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                IconButton(onClick = { viewModel.setShowDatePicker(!viewModel.getShowDatePicker()) }) {
                    Icon(
                        painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = { }) {
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
) {
    val rememberDatePickerState = rememberDatePickerState()
    val deadlineUiViewState by viewModel.deadlineUiViewState.collectAsState()
    val todoState = viewModel.todoUiState.todoState
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val isInputDeadlineState =
            viewModel.isInputTimePickerState || viewModel.isInputDatePickerState
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
                onClick = { viewModel.setShowDatePicker(!viewModel.getShowDatePicker()) },
                selected = false,
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.updateIsInputTimePickerState(false)
                        viewModel.updateIsInputDatePickerState(false)
                        viewModel.resetTimePickerState()
                        viewModel.resetDatePickerState()
                    }) {
                        Icon(
                            painterResource(id = R.drawable.round_close_24),
                            contentDescription = "Localized description",
                        )
                    }
                },
            )
        }

        if (viewModel.getShowDatePicker()) {
            DatePickerComponent(
                viewModel = viewModel,
                rememberDatePickerState = rememberDatePickerState,
            )
        }
        if (viewModel.getShowTimePicker()) {
            TimePickerComponent(viewModel = viewModel)
        }
    }
}
