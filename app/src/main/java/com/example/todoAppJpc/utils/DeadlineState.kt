package com.example.todoAppJpc.utils

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Locale

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

@OptIn(ExperimentalMaterial3Api::class)
fun DeadlineUiState.getDeadlineUiState(): String {
    val userLocale = Locale.getDefault()
    val cal = Calendar.getInstance()

    val timeFormatter = SimpleDateFormat("HH:mm", userLocale)
    val timeState = deadlineState.timePickerState
    cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
    cal.set(Calendar.MINUTE, timeState.minute)
    cal.isLenient = false
    val timeUiState =
        if (isInputDeadlineState.isInputTimePickerState) timeFormatter.format(cal.time) else ""

    val dateFormatter = SimpleDateFormat("MM/dd(EEE)", userLocale)
    val dateState = deadlineState.datePickerState
    cal.apply {
        timeInMillis = dateState.selectedDateMillis!!
    }
    val dateUiState =
        if (isInputDeadlineState.isInputDatePickerState) dateFormatter.format(cal.time) else ""

    return "$dateUiState $timeUiState"


}