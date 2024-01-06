package com.example.todoAppJpc.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.DatePickerViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.DeadlinePickerViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.TimePickerViewModel

class DeadlinePickerViewModelImpl(
    override var _datePickerViewModel: DatePickerViewModel,
    override var _timePickerViewModel: TimePickerViewModel,
) : ViewModel(), DeadlinePickerViewModel