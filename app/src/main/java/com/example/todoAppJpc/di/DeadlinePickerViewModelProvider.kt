package com.example.todoAppJpc.di

import com.example.todoAppJpc.ui.viewmodel.DatePickerViewModelImpl
import com.example.todoAppJpc.ui.viewmodel.TimePickerViewModelImpl
import com.example.todoAppJpc.utils.deadline.viewModel.DatePickerViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.TimePickerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DeadlinePickerViewModelProvider {
    @Provides
    fun datePickerViewModelProvider(): DatePickerViewModel {
        return DatePickerViewModelImpl()
    }

    @Provides
    fun timePickerViewModelProvider(): TimePickerViewModel {
        return TimePickerViewModelImpl()
    }

}