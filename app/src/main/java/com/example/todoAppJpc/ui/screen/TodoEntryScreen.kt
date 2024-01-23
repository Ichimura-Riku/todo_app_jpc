package com.example.todoAppJpc.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoAppJpc.R
import com.example.todoAppJpc.ui.components.DatePickerComponent
import com.example.todoAppJpc.ui.components.TimePickerComponent
import com.example.todoAppJpc.ui.viewmodel.TodoEntryViewModel
import com.example.todoAppJpc.ui.viewmodel.TodoState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEntryBody(
    viewModel: TodoEntryViewModel = hiltViewModel(),
    onTodoValueChange: (TodoState) -> Unit = viewModel::updateTodoState,
    closeBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val showDatePicker =
        viewModel.datePickerViewModel.showDatePicker.collectAsState()
    val isShowChip = rememberSaveable { mutableStateOf(false) }
    val isShowContentTextField = rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val datePickerViewModel = viewModel.datePickerViewModel
    val timePickerViewModel = viewModel.timePickerViewModel
    val datePickerState = datePickerViewModel.datePickerState.collectAsState()
    val timePickerState = timePickerViewModel.timePickerState.collectAsState()
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {

            TodoInputForm(
                viewModel = viewModel,
                onValueChange = onTodoValueChange,
                modifier = Modifier.fillMaxWidth(),
                isShowChip = isShowChip,
                isShowContentTextField = isShowContentTextField,
            )

            Button(
                onClick = {
                    /**
                     * この関数が実行される時、deadlinePickerViewModelの値をTodoStateに代入する処理も一緒に行う。
                     */

                    viewModel.adventTodo(
                        datePickerState = datePickerState.value,
                        timePickerState = timePickerState.value,
                    )
                    closeBottomSheet()
                    isShowChip.value = false
                    isShowContentTextField.value = false
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.add_action))
            }
        }
        BottomAppBar(
            modifier = Modifier.statusBarsPadding(),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                IconButton(onClick = {
                    isShowContentTextField.value = !isShowContentTextField.value
                }) {
                    Icon(
                        painterResource(id = if (isShowContentTextField.value) R.drawable.baseline_edit_note_24 else R.drawable.baseline_notes_24),
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = {
                    viewModel.datePickerViewModel.setShowDatePicker(
                        !showDatePicker.value
                    )
                }) {
                    Icon(
                        painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = {
                    viewModel.datePickerViewModel.setShowDatePicker(
                        !showDatePicker.value
                    )
                }) {
                    Icon(
                        painterResource(id = R.drawable.round_more_horiz_24),
                        contentDescription = "Localized description",
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoInputForm(
    viewModel: TodoEntryViewModel,
    modifier: Modifier = Modifier,
    onValueChange: (TodoState) -> Unit = {},
    isShowChip: MutableState<Boolean>,
    isShowContentTextField: MutableState<Boolean>,
) {
    val todoState = viewModel.todoUiState.todoState
    val datePickerViewModel = viewModel.datePickerViewModel
    val timePickerViewModel = viewModel.timePickerViewModel
    val showDatePicker = datePickerViewModel.showDatePicker.collectAsState()
    val showTimePicker = timePickerViewModel.showTimePicker.collectAsState()
    val datePickerState = datePickerViewModel.datePickerState.collectAsState()
    val timePickerState = timePickerViewModel.timePickerState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TextField(
            value = todoState.title,
            onValueChange = { onValueChange(todoState.copy(title = it)) },
            label = { Text(stringResource(R.string.todo_title_req)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
        )

        if (isShowContentTextField.value) {
            TextField(
                value = todoState.content,
                onValueChange = { onValueChange(todoState.copy(content = it)) },
                label = { Text(stringResource(R.string.todo_content_req)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
            )
        }

        if (isShowChip.value) {
            InputChip(
                label = {
                    ChipText(
                        datePickerState = datePickerState.value,
                        timePickerState = timePickerState.value
                    )
                },
                onClick = { datePickerViewModel.setShowDatePicker(!showDatePicker.value) },
                selected = false,
                trailingIcon = {
                    IconButton(onClick = { // Todo: reset処理
                        isShowChip.value = false
                        datePickerViewModel.resetDatePickerState()
                        timePickerViewModel.resetTimePickerState()
                    }) {
                        Icon(
                            painterResource(id = R.drawable.round_close_24),
                            contentDescription = "Localized description",
                        )
                    }
                },
            )
        }
        
        if (showDatePicker.value) {
            DatePickerComponent(
                datePickerViewModel = datePickerViewModel,
                timePickerViewModel = timePickerViewModel,
                setChipView = { isShowChip.value = true },
                modifier = modifier,
            )
        }
        if (showTimePicker.value) {
            TimePickerComponent(
                datePickerViewModel = datePickerViewModel,
                timePickerViewModel = timePickerViewModel,
                setChipView = { isShowChip.value = true },

                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipText(
    datePickerState: DatePickerState,
    timePickerState: TimePickerState,
) {
    val userLocale = Locale.getDefault()
    val cal = Calendar.getInstance()

    val timeFormatter = SimpleDateFormat("HH:mm", userLocale)
    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
    cal.set(Calendar.MINUTE, timePickerState.minute)
    cal.isLenient = false
    val timeUiState = timeFormatter.format(
        cal.time
    )
    val dateFormatter = SimpleDateFormat("MM/dd(EEE)", userLocale)

    try {
        cal.apply { timeInMillis = datePickerState.selectedDateMillis!! }
    } catch (e: Exception) {
        Log.e("getDeadlineUiState() is error", "$e")
    }

    val dateUiState =
        dateFormatter.format(
            cal.time
        )

    Text("$dateUiState $timeUiState")
}
