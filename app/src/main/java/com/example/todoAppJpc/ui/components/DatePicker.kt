package com.example.todoAppJpc.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.todoAppJpc.ui.todo.TodoEntryViewModel
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    viewModel: TodoEntryViewModel,

    rememberDatePickerState: DatePickerState,
) {
    Material3DatePickerDialogComponent(
        viewModel = viewModel,
        rememberDatePickerState = rememberDatePickerState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3DatePickerDialogComponent(
    viewModel: TodoEntryViewModel,
    rememberDatePickerState: DatePickerState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val closePicker = { viewModel.setShowDatePicker(false) }
    val showTimePicker = { viewModel.setShowTimePicker(true) }
    val datePickerStateSet = {
        scope.async {
            viewModel.updateDeadlineUiViewState()

            // ui表示用のstate生成関数
//        viewModel.updateDeadlineUiViewState()
            // stateに入力されたかどうかのフラグで、こいつが変化することをscreenがみているので
            // 処理の最後に入れるのが妥当と考えている。もし反映されてないのであれば、こいつが
            // 上の関数よりも早く動いている可能性がある。
        }
        viewModel.updateIsInputDatePickerState(true)
    }

    fun updateDatePickerState(selectedDateMillis: Long?) =
        viewModel.updateDatePickerState(selectedDateMillis)

    DatePickerDialog(
        onDismissRequest = {
            closePicker()
        },
        confirmButton = {
            Row {
                TextButton(
                    onClick = {
                        // こいつはDatePickerStateクラスのデフォルトのメソッドで更新されていて、この値を
                        // flowで監視するには新しくクラスを定義する必要があるかもしれない
                        // overrideで定義するか？
                        // こいつの反応が遅かったら今の状況は納得できる
                        // 少なくとも、この値はコルーチンでawaitさせたい
                        updateDatePickerState(rememberDatePickerState.selectedDateMillis)
                        datePickerStateSet()
                        // ちなみにclosePickerはui反映に影響しない
                        closePicker()
                    },
                ) {
                    Text(text = "OK")
                }
                TextButton(
                    onClick = {
                        updateDatePickerState(rememberDatePickerState.selectedDateMillis)
                        showTimePicker()
                        // ちなみにclosePickerはui反映に影響しない
                        closePicker()
                        datePickerStateSet()
                    },
                ) {
                    Text(text = "set time")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closePicker()
                },
            ) {
                Text(text = "CANCEL")
            }
        },
        modifier = modifier,
    ) {
        DatePicker(state = rememberDatePickerState)
    }
}
