package com.example.todoAppJpc.ui.viewmodel

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import com.example.todoAppJpc.data.TodoEntity
import com.example.todoAppJpc.data.TodoRepository
import com.example.todoAppJpc.utils.deadline.viewModel.DeadlinePickerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class, SavedStateHandleSaveableApi::class)
@HiltViewModel
class TodoEntryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val _todoRepository: TodoRepository,
    private val _deadlinePickerViewModel: DeadlinePickerViewModel,
) : ViewModel() {
    var todoUiState by mutableStateOf(TodoUiState())
        private set

    // ---------------- [deadlineState] ----------------
    val deadlinePickerViewModel get() = _deadlinePickerViewModel

    private var _deadlineUiViewState: MutableStateFlow<String> = MutableStateFlow("")

    val deadlineUiViewState: StateFlow<String> get() = _deadlineUiViewState

    private fun setDeadlineStateToTodoState(
        datePickerState: DatePickerState,
        timePickerState: TimePickerState,
    ) {
        val inputDeadlineTimeHour = 10000 + timePickerState.hour * 100
        val inputDeadlineTimeMinute = timePickerState.minute
        val inputDeadlineDate = datePickerState.selectedDateMillis!!

        updateTodoState(
            todoUiState.todoState.copy(
                deadlineDate = inputDeadlineDate,
                deadlineTimeHour = inputDeadlineTimeHour,
                deadlineTimeMinute = inputDeadlineTimeMinute,
            )
        )
    }


    fun updateTodoState(todoState: TodoState) {
        todoUiState = TodoUiState(todoState = todoState)
    }

    private fun resetTodoState() {
        todoUiState = TodoUiState()
        _deadlinePickerViewModel.datePickerViewModel.resetDatePickerState()
        _deadlinePickerViewModel.timePickerViewModel.resetTimePickerState()
    }

    fun adventTodo(
        datePickerState: DatePickerState,
        timePickerState: TimePickerState,
    ) {
        viewModelScope.launch {
            setDeadlineStateToTodoState(datePickerState, timePickerState)
            withContext(Dispatchers.IO) {
                _todoRepository.insertTodo(todoUiState.todoState.toTodo())
            }
            resetTodoState()
        }
    }
}

data class TodoUiState(
    val todoState: TodoState = TodoState(),

    )

// ItemDetailsの代わり
data class TodoState(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val date: String = "",
    var deadlineDate: Long = -1000000000000,
    val deadlineTimeHour: Int = 10000,
    val deadlineTimeMinute: Int = 100,
    val isAttention: Int = 0,
    val category: String = "myTask",
    val isFinished: Int = 0,
    val priority: String = "low",
)

fun TodoState.toTodo(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    content = content,
//    多分日時系は型変換する必要がある
    date = date,
    deadline = deadlineDate + deadlineTimeHour + deadlineTimeMinute % 100,
    isAttention = isAttention,
    category = category,
    isFinished = isFinished,
    priority = priority,
)

fun TodoEntity.toTodoUiState(): TodoUiState = TodoUiState(
    todoState = this.toTodoState(),
)

fun TodoEntity.toTodoState(): TodoState = TodoState(
    id = id,
    title = title,
    content = content,
//    多分日時系は型変換する必要がある
    date = date,
    deadlineDate = deadline / 100000 * 100000,
    deadlineTimeHour = ((deadline % 100000) / 100).toInt() * 100,
    deadlineTimeMinute = (deadline % 100).toInt() + 100,
    isAttention = isAttention,
    category = category,
    isFinished = isFinished,
    priority = priority,
)