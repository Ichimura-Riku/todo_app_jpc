package com.example.todoAppJpc.utils.deadline.viewModel

import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
interface DeadlinePickerViewModel {
    var _datePickerViewModel: DatePickerViewModel
    var _timePickerViewModel: TimePickerViewModel
}