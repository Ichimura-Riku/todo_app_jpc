package com.example.todoAppJpc.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoAppJpc.ui.todo.TodoEntryViewModel
import java.time.Instant

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
            closePicker = {
                viewModel.setShowDatePicker(false)
                viewModel.setShowTimePicker(true)
            },
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
