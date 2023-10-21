package com.example.todoAppJpc.ui.components

import android.util.Log
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todoAppJpc.ui.todo.TodoEntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    viewModel: TodoEntryViewModel,
) {
    if (viewModel.getShowDatePicker()) {
        Material3DatePickerDialogComponent(
            datePickerState = viewModel.datePickerState,
            closePicker = {
                viewModel.setShowDatePicker(false)
                viewModel.setShowTimePicker(true)
                Log.d("debug-----", "${viewModel.datePickerState.selectedDateMillis}")
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
                },
            ) {
                Text(text = "OK")
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
