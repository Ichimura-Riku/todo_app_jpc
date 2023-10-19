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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoAppJpc.R
import java.time.Instant


@Composable
fun TodoEntryBody(
    viewModel: TodoEntryViewModel = hiltViewModel(),
    todoState: TodoState,
    onTodoValueChange: (TodoState) -> Unit,
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
                todoState = todoState,
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
//                horizontalArrangement = Arrangement.SpaceBetween,
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
                        painterResource(id = R.drawable.round_swap_vert_24),
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = { /* doSomething() */ }) {
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
    todoState: TodoState,
    modifier: Modifier = Modifier,
    onValueChange: (TodoState) -> Unit = {},
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
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
        if (viewModel.getShowDatePicker()) {

            DatePickerComponent(viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    viewModel: TodoEntryViewModel
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    if (viewModel.getShowDatePicker()) {
        Material3DatePickerDialogComponent(
            datePickerState = datePickerState,
            closePicker = { viewModel.setShowDatePicker(false) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3DatePickerDialogComponent(
    datePickerState: DatePickerState,
    closePicker: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DatePickerDialog(
        onDismissRequest = {
            closePicker()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.setSelection(datePickerState.selectedDateMillis)
                    closePicker()
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closePicker()
                }
            ) {
                Text(text = "CANCEL")
            }
        },
        modifier = modifier,
    ) {
        DatePicker(state = datePickerState)
    }
}
