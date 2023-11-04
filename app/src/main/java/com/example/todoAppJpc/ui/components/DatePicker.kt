package com.example.todoAppJpc.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.todoAppJpc.ui.todo.TodoEntryViewModel
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    viewModel: TodoEntryViewModel,
    rememberDatePickerState: DatePickerState = rememberDatePickerState(),
) {
    Material3DatePickerDialogComponent(
        viewModel = viewModel,
        rememberDatePickerState = rememberDatePickerState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3DatePickerDialogComponent(
    viewModel: TodoEntryViewModel,
    rememberDatePickerState: DatePickerState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val closePicker = { viewModel.setShowDatePicker(false) }
    val showTimePicker = { viewModel.setShowTimePicker(true) }
    val datePickerStateSet = {
        val result = scope.async {
            viewModel.updateDeadlineUiViewState()
        }
        result.onAwait
        viewModel.updateIsInputDatePickerState(true)
    }

    val updateDatePickerState: (selectedDateMillis: Long?) -> Unit = { selectedDateMillis ->
        scope.async {
            viewModel.updateDatePickerState(selectedDateMillis)
        }.onAwait
    }

    DatePickerDialog(
        onDismissRequest = {
            closePicker()
        },
        confirmButton = {
            Row {
                TextButton(
                    onClick = {
                        updateDatePickerState(rememberDatePickerState.selectedDateMillis)
                        datePickerStateSet()
                        closePicker()
                    },
                ) {
                    Text(text = "OK")
                }
                TextButton(
                    onClick = {
                        updateDatePickerState(rememberDatePickerState.selectedDateMillis)
                        showTimePicker()
                        closePicker()
                        datePickerStateSet()
                    },
                ) {
                    Text(text = "set time")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closePicker()
                },
            ) {
                Text(text = "CANCEL")
            }
        },
        modifier = modifier,
    ) {
        DatePicker(state = rememberDatePickerState)
    }
}
