package com.example.todoAppJpc.utils

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.time.Instant

data class DeadlineUiState
@OptIn(ExperimentalMaterial3Api::class)
constructor(
    val deadlineState: DeadlineState = DeadlineState(),
    val showDeadlineDialog: ShowDeadlineDialog = ShowDeadlineDialog(),
    val isInputDeadlineState: IsInputDeadlineState = IsInputDeadlineState(),
)

data class DeadlineState
@OptIn(ExperimentalMaterial3Api::class)
constructor(
    var datePickerState: DatePickerState = DatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
        initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
        yearRange = DatePickerDefaults.YearRange,
        initialDisplayMode = DisplayMode.Picker,
    ),
    var timePickerState: TimePickerState = TimePickerState(0, 0, true),

    )

data class ShowDeadlineDialog(
    var showDatePicker: Boolean = false,
    var showTimePicker: Boolean = false,

    )

data class IsInputDeadlineState(
    var isInputTimePickerState: Boolean = false,
    var isInputDatePickerState: Boolean = false,
)

fun DeadlineUiState.updateIsInputTimePickerState(isInputState: Boolean) {
    isInputDeadlineState.isInputTimePickerState = isInputState
}

fun DeadlineUiState.updateIsInputDatePickerState(isInputState: Boolean) {
    isInputDeadlineState.isInputDatePickerState = isInputState
}
