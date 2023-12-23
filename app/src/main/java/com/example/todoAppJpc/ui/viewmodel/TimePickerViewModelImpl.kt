package com.example.todoAppJpc.ui.viewmodel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.example.todoAppJpc.utils.deadline.viewModel.TimePickerViewModel

@OptIn(ExperimentalMaterial3Api::class)
class TimePickerViewModelImpl : TimePickerViewModel {

    private var _timePickerState: TimePickerState = TimePickerState(0, 0, true)

    private var _showTimePicker: Boolean = false

    override val timePickerState: TimePickerState get() = _timePickerState

    override val showTimePicker: Boolean get() = _showTimePicker

    override fun setTimePickerState(value: TimePickerState) {
        _timePickerState = value
    }

    override fun resetTimePickerState() {
        _timePickerState = TimePickerState(0, 0, true)
    }

    override fun setShowTimePicker(value: Boolean) {
        _showTimePicker = value
    }
}