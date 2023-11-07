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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.todoAppJpc.utils.DeadlineUiState
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
//    viewModel: TodoEntryViewModel,
    deadlineUiState: DeadlineUiState,
    showDatePickerMutableState: MutableState<Boolean>,
    showTimePickerMutableState: MutableState<Boolean>,
    updateDeadlineUiViewState: suspend () -> Unit,
    rememberDatePickerState: DatePickerState = rememberDatePickerState(),
) {
    Material3DatePickerDialogComponent(
//        viewModel = viewModel,
        deadlineUiState = deadlineUiState,
        updateDeadlineUiViewState = updateDeadlineUiViewState,
        showDatePickerMutableState = showDatePickerMutableState,
        showTimePickerMutableState = showTimePickerMutableState,
        rememberDatePickerState = rememberDatePickerState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3DatePickerDialogComponent(
    deadlineUiState: DeadlineUiState,
    updateDeadlineUiViewState: suspend () -> Unit,
    rememberDatePickerState: DatePickerState,
    showDatePickerMutableState: MutableState<Boolean>,
    showTimePickerMutableState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {

    val scope = rememberCoroutineScope()
    var showDatePickerState by showDatePickerMutableState
    var showTimePickerState by showTimePickerMutableState
    val closePicker = { showDatePickerState = false }
    val showTimePicker = { }
    val datePickerStateSet = {
        val result = scope.async {
            updateDeadlineUiViewState()
        }
        result.onAwait
        deadlineUiState.updateIsInputDatePickerState(true)
    }

    val updateDatePickerState: (selectedDateMillis: Long?) -> Unit = { selectedDateMillis ->
        scope.async {
            deadlineUiState.updateDatePickerState(selectedDateMillis)
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
