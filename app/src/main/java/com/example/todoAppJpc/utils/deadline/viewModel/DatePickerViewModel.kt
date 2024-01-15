package com.example.todoAppJpc.utils.deadline.viewModel

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
interface DatePickerViewModel {

    // 日付の値を取得する
    val datePickerState: StateFlow<DatePickerState>

    // 日付選択ダイアログを表示するかどうかを取得する
    val showDatePicker: StateFlow<Boolean>

    // 日付の値をセットする
    fun setDatePickerState(value: DatePickerState)

    // 日付選択ダイアログの状態をリセットする
    fun resetDatePickerState()

    // 日付選択ダイアログを表示するかどうかをセットする
    fun setShowDatePicker(value: Boolean)
}