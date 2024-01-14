package com.example.todoAppJpc.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.todoAppJpc.utils.deadline.viewModel.DeadlinePickerViewModel
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    deadlinePickerViewModel: DeadlinePickerViewModel,
    setChipView: suspend () -> Unit = {},
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    val timePickerViewModel = deadlinePickerViewModel.timePickerViewModel
    val datePickerViewModel = deadlinePickerViewModel.datePickerViewModel
    val hideDatePicker = { datePickerViewModel.setShowDatePicker(false) }
    val showTimePicker = { timePickerViewModel.setShowTimePicker(true) }
    val datePickerState = datePickerViewModel.datePickerState.collectAsState()
    val datePickerStateSet = {
        val result = scope.async {
            setChipView()
        }
        result.onAwait
    }

    DatePickerDialog(
        onDismissRequest = {
            hideDatePicker()
        },
        confirmButton = {
            Row {
                TextButton(
                    onClick = {
                        datePickerStateSet()
                        hideDatePicker()
                    },
                ) {
                    Text(text = "OK")
                }
                TextButton(
                    onClick = {
                        showTimePicker()
                        hideDatePicker()
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
                    hideDatePicker()
                },
            ) {
                Text(text = "CANCEL")
            }
        },
        modifier = modifier,
    ) {
        DatePicker(state = datePickerState.value)
    }
}
