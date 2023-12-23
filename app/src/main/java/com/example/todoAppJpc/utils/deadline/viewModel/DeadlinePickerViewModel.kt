package com.example.todoAppJpc.utils.deadline.viewModel

import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
interface DeadlinePickerViewModel {
    val datePickerViewModel: DatePickerViewModel
    val timePickerViewModel: DatePickerViewModel
}