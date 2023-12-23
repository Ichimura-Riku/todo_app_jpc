package com.example.todoAppJpc.utils.deadline.viewModel

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
interface TimePickerViewModel {

    // 時間の値を取得する
    fun timePickerState(): DatePickerState

    // 時間選択ダイアログを表示するかどうかを取得する
    fun showtimePicker(): Boolean

    // 時間の値をセットする
    fun setTimePickerState(value: DatePickerState)

    // 時間選択ダイアログの状態をリセットする
    fun resetTimePickerState()

    // 時間選択ダイアログを表示するかどうかをセットする
    fun setShowTimePicker(value: Boolean)
}
