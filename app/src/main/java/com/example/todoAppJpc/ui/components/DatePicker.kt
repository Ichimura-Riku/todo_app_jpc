package com.example.todoAppJpc.ui.components

import androidx.compose.foundation.layout.Row
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
    rememberDatePickerState: DatePickerState,
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
    val closePicker = { viewModel.setShowDatePicker(false) }
    val showTimePicker = { viewModel.setShowTimePicker(true) }
    val datePickerStateSet = { viewModel.updateIsInputDatePickerState(true) }
    fun updateDatePickerState(selectedDateMillis: Long?) =
        viewModel.updateDatePickerState(selectedDateMillis)

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
