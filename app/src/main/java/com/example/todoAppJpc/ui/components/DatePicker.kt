package com.example.todoAppJpc.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoAppJpc.ui.todo.TodoEntryViewModel

@Composable
fun DatePickerComponent(
    viewModel: TodoEntryViewModel,
) {
    if (viewModel.getShowDatePicker()) {
        Material3DatePickerDialogComponent(
            viewModel = viewModel,
            closePicker = {
                viewModel.setShowDatePicker(false)
            },
            showTimePicker = {
                viewModel.setShowTimePicker(true)
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3DatePickerDialogComponent(
    viewModel: TodoEntryViewModel,
    closePicker: () -> Unit,
    showTimePicker: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val datePickerState = viewModel.datePickerState
    DatePickerDialog(
        onDismissRequest = {
            closePicker()
        },
        confirmButton = {
            Row {
                TextButton(
                    onClick = {
                        viewModel.updateIsInputDatePickerState(true)
                        datePickerState.setSelection(datePickerState.selectedDateMillis)
                        closePicker()
                    },
                ) {
                    Text(text = "OK")
                }
                TextButton(
                    onClick = {
                        viewModel.updateIsInputDatePickerState(true)
                        datePickerState.setSelection(datePickerState.selectedDateMillis)
                        closePicker()
                        showTimePicker()
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
        DatePicker(state = datePickerState)
    }
}
