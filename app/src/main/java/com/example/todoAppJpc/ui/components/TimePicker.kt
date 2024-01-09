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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.todoAppJpc.utils.deadline.viewModel.DeadlinePickerViewModel
import kotlinx.coroutines.async


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerComponent(
    title: String = "Set Time",
    deadlinePickerViewModel: DeadlinePickerViewModel,
    setChipView: suspend () -> Unit,
    toggle: @Composable () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val timePickerViewModel = deadlinePickerViewModel.timePickerViewModel
    val datePickerViewModel = deadlinePickerViewModel.datePickerViewModel
    val showDatePickerState = datePickerViewModel.showDatePicker
    val hideTimePicker = { timePickerViewModel.setShowTimePicker(false) }
    val showDatePicker = { datePickerViewModel.setShowDatePicker(true) }
    val timePickerState = timePickerViewModel.timePickerState

    val timePickerStateSet = {
        val result = scope.async {
            /**
             * 設定した日時をChipに表示して、ViewModelで保持しているTodoの値を更新する
             * Chipの値表示はEntryにもEditにもいるのでdeadlineViewModelに入れる
             * Todoの値の更新はdeadlineの範囲を超えているんで、各ViewModelで適宜入れる
             */
            setChipView()
        }
        result.onAwait
    }
    Dialog(
        onDismissRequest = hideTimePicker,
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
                        onClick = hideTimePicker,
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = {
                            hideTimePicker()
                            timePickerStateSet()
                        },
                    ) { Text("OK") }
                    TextButton(
                        onClick = {
                            hideTimePicker()
                            timePickerStateSet()
                            showDatePicker()
                        },
                    ) { Text("set Date") }
                }
            }
        }
    }
}
