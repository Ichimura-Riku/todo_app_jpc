package com.example.todoAppJpc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.todoAppJpc.utils.DeadlineUiState
import kotlinx.coroutines.async

@Composable
fun TimePickerComponent(
//    viewModel: TodoEntryViewModel,
    deadlineUiState: DeadlineUiState,
    showDatePickerMutableState: MutableState<Boolean>,
    showTimePickerMutableState: MutableState<Boolean>,
    updateDeadlineUiViewState: suspend () -> Unit,
) {
    Material3TimePickerDialogComponent(
        deadlineUiState = deadlineUiState,
        showDatePickerMutableState = showDatePickerMutableState,
        showTimePickerMutableState = showTimePickerMutableState,
        updateDeadlineUiViewState = { updateDeadlineUiViewState() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3TimePickerDialogComponent(
    title: String = "Select Time",
//    viewModel: TodoEntryViewModel,
    deadlineUiState: DeadlineUiState,
    showDatePickerMutableState: MutableState<Boolean>,
    showTimePickerMutableState: MutableState<Boolean>,
    updateDeadlineUiViewState: suspend () -> Unit,
    toggle: @Composable () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    var showDatePickerState by showDatePickerMutableState
    var showTimePickerState by showTimePickerMutableState
    val closePicker = { showTimePickerState = false }
    val showDatePicker = { showDatePickerState = true }
    val timePickerState = deadlineUiState.deadlineState.timePickerState
    val timePickerStateSet = {
        val result = scope.async {
            updateDeadlineUiViewState()
        }
        result.onAwait
        deadlineUiState.updateIsInputTimePickerState(true)
    }
    Dialog(
        onDismissRequest = closePicker,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface,
                ),
        ) {
            toggle()
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                )
                TimePicker(state = timePickerState)
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = closePicker,
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = {
                            closePicker()
                            timePickerStateSet()
                        },
                    ) { Text("OK") }
                    TextButton(
                        onClick = {
                            closePicker()
                            timePickerStateSet()
                            showDatePicker()
                        },
                    ) { Text("set Date") }
                }
            }
        }
    }
}
