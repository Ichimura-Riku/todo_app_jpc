package com.example.todoAppJpc.ui.viewmodel

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoAppJpc.utils.deadline.viewModel.DatePickerViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.DeadlinePickerViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.TimePickerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    //        override suspend fun getChipText(): String { //Todo 変更予定の関数名
    override suspend fun updateDeadlineUiState(): String {
        /**
         * Chipを表示させるかどうかを判定する
         * 一旦初期値を確認したい
         * 初期値で比較して、もし何も入力されていないなら表示されない。
         * 入力されているのであれば表示する文字列を返す
         * 締切日時が入力されるのであれば必ず日付が入力されるんで、日付があるかどうかだけ確認すればいい
         */

        val userLocale = Locale.getDefault()
        val cal = Calendar.getInstance()

        val timeFormatter = SimpleDateFormat("HH:mm", userLocale)
        val timeState = _timePickerViewModel.timePickerState
        cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
        cal.set(Calendar.MINUTE, timeState.minute)
        cal.isLenient = false
        val timeUiState =
            if (isShowChip()) timeFormatter.format(
                cal.time
            ) else ""
        val dateFormatter = SimpleDateFormat("MM/dd(EEE)", userLocale)

        try {
            val setCalApply = viewModelScope.async(Dispatchers.IO) {
                val dateState = _datePickerViewModel.datePickerState
                cal.apply {
                    timeInMillis = dateState.selectedDateMillis!!
                }
            }.await()
            setCalApply
        } catch (e: Exception) {
            Log.e("getDeadlineUiState() is error", "$e")
        }

        val dateUiState =
            if (isShowChip()) dateFormatter.format(
                cal.time
            ) else ""

        return "$dateUiState $timeUiState"

    }

    override fun isShowChip(): Boolean {
        return _datePickerViewModel.datePickerState.selectedDateMillis!! > 0L
    }


}