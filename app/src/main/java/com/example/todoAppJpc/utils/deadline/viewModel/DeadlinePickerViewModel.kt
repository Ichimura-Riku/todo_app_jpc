package com.example.todoAppJpc.utils.deadline.viewModel

import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
interface DeadlinePickerViewModel {
    val datePickerViewModel: DatePickerViewModel
    val timePickerViewModel: TimePickerViewModel

    // 設定した日時をChipに表示する関数
    // override suspend fun getChipText(): String { //Todo 変更予定の関数名
    suspend fun updateDeadlineUiState(): String

    // Chipを表示させるかどうかを判定する関数
    fun isShowChip(): Boolean


}