package com.example.todoAppJpc.utils.deadline.viewModel

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
interface DatePickerViewModel {

    // 日付の値を取得する
    fun datePickerState(): DatePickerState

    // 日付選択ダイアログを表示するかどうかを取得する
    fun showDatePicker(): Boolean

    // 日付の値をセットする
    fun setDatePickerState(value: DatePickerState)

    // 日付選択ダイアログの状態をリセットする
    fun resetDatePickerState()

    // 日付選択ダイアログを表示するかどうかをセットする
    fun setShowDatePicker(value: Boolean)
}