package com.example.todoAppJpc.ui.viewmodel

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.todoAppJpc.utils.deadline.viewModel.DatePickerViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
class DatePickerViewModelImpl : DatePickerViewModel {
    private val _datePickerState = MutableStateFlow(
        DatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli(),
            initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker,
        )
    )

    private val _showDatePicker = MutableStateFlow(false)

    override val datePickerState: StateFlow<DatePickerState> = _datePickerState

    override val showDatePicker: StateFlow<Boolean> = _showDatePicker

    override fun setDatePickerState(value: DatePickerState) {
        _datePickerState.value = value
    }

    override fun resetDatePickerState() {
        _datePickerState.value = DatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli(),
            initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker,
        )
    }

    override fun setShowDatePicker(value: Boolean) {
        _showDatePicker.value = value
    }
}