package com.example.todoAppJpc.utils.deadline.viewModel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
interface TimePickerViewModel {

    // 時間の値を取得する
    val timePickerState: StateFlow<TimePickerState>

    // 時間選択ダイアログを表示するかどうかを取得する
    val showTimePicker: StateFlow<Boolean>

    // 時間の値をセットする
    fun setTimePickerState(value: TimePickerState)

    // 時間選択ダイアログの状態をリセットする
    fun resetTimePickerState()

    // 時間選択ダイアログを表示するかどうかをセットする
    fun setShowTimePicker(value: Boolean)
}
