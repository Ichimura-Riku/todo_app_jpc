package com.example.todoAppJpc.ui.viewmodel

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.todoAppJpc.utils.deadline.viewModel.DatePickerViewModel
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
class DatePickerViewModelImpl : DatePickerViewModel {

    private var _datePickerState: DatePickerState = DatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
        initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
        yearRange = DatePickerDefaults.YearRange,
        initialDisplayMode = DisplayMode.Picker,
    )

    private var _showDatePicker: Boolean = false

    override val datePickerState: DatePickerState get() = _datePickerState

    override val showDatePicker: Boolean get() = _showDatePicker

    override fun setDatePickerState(value: DatePickerState) {
        _datePickerState = value
    }

    override fun resetDatePickerState() {
        _datePickerState = DatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli(),
            initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker,
        )
    }

    override fun setShowDatePicker(value: Boolean) {
        _showDatePicker = value
    }
}