package com.example.todoAppJpc.ui.viewmodel

import android.util.Log
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoAppJpc.utils.deadline.viewModel.DatePickerViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.DeadlinePickerViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.TimePickerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class DeadlinePickerViewModelImpl(
    private var _datePickerViewModel: DatePickerViewModel,
    private var _timePickerViewModel: TimePickerViewModel,
) : ViewModel(), DeadlinePickerViewModel {
    override val datePickerViewModel get() = _datePickerViewModel
    override val timePickerViewModel get() = _timePickerViewModel
}