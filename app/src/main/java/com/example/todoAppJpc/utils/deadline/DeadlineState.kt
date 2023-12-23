package com.example.todoAppJpc.utils.deadline

import android.util.Log
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

//[deadlineState]
// deadline(Picker)State
// isInputDeadlineState
// deadlineUiViewState(Uiに直接表示するための値)
//[showDatePicker]
//[showTimePicker]
@OptIn(ExperimentalMaterial3Api::class)
data class DeadlineUiState constructor(
    private var _deadlineState: DeadlineState = DeadlineState(),
//    private var _deadlineUiViewState: MutableStateFlow<String> = MutableStateFlow(""),

    // isInputDeadlineState
    private var _isInputDatePickerState: Boolean = false,
    private var _isInputTimePickerState: Boolean = false,

    //[showDeadlinePicker]
    private var _showDatePicker: Boolean = false,
    private var _showTimePicker: Boolean = false,

    ) {

    //    val datePickerState: DatePickerState get() = _deadlineState.datePickerState
//    val timePickerState: TimePickerState get() = _deadlineState.timePickerState
    val deadlineState: DeadlineState get() = _deadlineState

    //    val deadlineUiViewState: StateFlow<String> get() = _deadlineUiViewState
    val isInputDatePickerState: Boolean get() = _isInputDatePickerState
    val isInputTimePickerState: Boolean get() = _isInputTimePickerState
    val showDatePicker: Boolean get() = _showDatePicker
    val showTimePicker: Boolean get() = _showTimePicker


    suspend fun updateDatePickerState(selectedDateMillis: Long?) {
        try {
            withContext(Dispatchers.IO) {
                deadlineState.datePickerState.setSelection(selectedDateMillis)
            }
        } catch (e: Exception) {
            Log.e("error is updateDatePickerState()", "$e")
        }
    }

    fun resetTimePickerState() {
        _deadlineState.timePickerState = TimePickerState(0, 0, true)
    }

    fun resetDatePickerState() {
        _deadlineState.datePickerState = DatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli(),
            initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker,
        )
    }

    fun updateIsInputTimePickerState(isInputState: Boolean) {
        _isInputTimePickerState = isInputState
    }

    fun updateIsInputDatePickerState(isInputState: Boolean) {
        _isInputDatePickerState = isInputState
    }
    // これはViewModel側で定義しないと行けなさそう


    fun setShowDatePicker(showDatePicker: Boolean) {
        _showDatePicker = showDatePicker
    }

    fun setShowTimePicker(showTimePicker: Boolean) {
        _showTimePicker = showTimePicker
    }

}

data class DeadlineState @OptIn(ExperimentalMaterial3Api::class) constructor(
    var datePickerState: DatePickerState = DatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
        initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
        yearRange = DatePickerDefaults.YearRange,
        initialDisplayMode = DisplayMode.Picker,
    ),
    var timePickerState: TimePickerState = TimePickerState(0, 0, true)
)


//fun DeadlineUiState.updateIsInputTimePickerState(isInputState: Boolean) {
//    isInputTimePickerState = isInputState
//}
//
//fun DeadlineUiState.updateIsInputDatePickerState(isInputState: Boolean) {
//    isInputDatePickerState = isInputState
//}


