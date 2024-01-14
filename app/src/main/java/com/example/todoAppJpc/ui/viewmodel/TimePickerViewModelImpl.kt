package com.example.todoAppJpc.ui.viewmodel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.example.todoAppJpc.utils.deadline.viewModel.TimePickerViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
class TimePickerViewModelImpl : TimePickerViewModel {

    private var _timePickerState = MutableStateFlow(TimePickerState(0, 0, true))

    private var _showTimePicker = MutableStateFlow(false)

    override val timePickerState: StateFlow<TimePickerState> = _timePickerState

    override val showTimePicker: StateFlow<Boolean> = _showTimePicker

    override fun setTimePickerState(value: TimePickerState) {
        _timePickerState.value = value
    }

    override fun resetTimePickerState() {
        _timePickerState.value = TimePickerState(0, 0, true)
    }

    override fun setShowTimePicker(value: Boolean) {
        _showTimePicker.value = value
    }
}