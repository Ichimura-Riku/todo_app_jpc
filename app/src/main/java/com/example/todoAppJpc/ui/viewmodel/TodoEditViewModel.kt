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
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.todoAppJpc.data.TodoRepository
import com.example.todoAppJpc.ui.screen.TodoEditDestination
import com.example.todoAppJpc.utils.deadline.viewModel.DatePickerViewModel
import com.example.todoAppJpc.utils.deadline.viewModel.TimePickerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class, SavedStateHandleSaveableApi::class)
@HiltViewModel
class TodoEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository,
    private val _datePickerViewModel: DatePickerViewModel,
    private val _timePickerViewModel: TimePickerViewModel,
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[TodoEditDestination.todoIdArg])
    var todoUiState by mutableStateOf(TodoUiState())
        private set
    private var _deleteConfirmationRequired by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    val datePickerViewModel get() = _datePickerViewModel
    val timePickerViewModel get() = _timePickerViewModel

    init {
        viewModelScope.launch {
            todoUiState = todoRepository.getTodoById(itemId)
                .filterNotNull()
                .first()
                .toTodoUiState()
            _datePickerViewModel.datePickerState.value.setSelection(
                todoUiState.todoState.deadlineDate
            )
            _timePickerViewModel.setTimePickerState(
                TimePickerState(
                    (todoUiState.todoState.deadlineTimeHour % 10000) / 100,
                    todoUiState.todoState.deadlineTimeMinute % 100,
                    true
                )
            )
        }
    }

    // ---------------- [deleteAction] ----------------
    fun getDeleteConfirmationRequired(): Boolean {
        return _deleteConfirmationRequired
    }

    fun setDeleteConfirmationRequired(deleteConfirmationRequired: Boolean) {
        _deleteConfirmationRequired = deleteConfirmationRequired
    }

    fun updateTodoState(todoState: TodoState) {
        todoUiState = TodoUiState(todoState = todoState)
    }

    private fun resetTodoState() {
        todoUiState = TodoUiState()
        _datePickerViewModel.resetDatePickerState()
        _timePickerViewModel.resetTimePickerState()
    }

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

    fun adventTodo(
        datePickerState: DatePickerState,
        timePickerState: TimePickerState,
    ) {
        viewModelScope.launch {
            setDeadlineStateToTodoState(datePickerState, timePickerState)
            withContext(Dispatchers.IO) {
                todoRepository.updateTodo(todoUiState.todoState.toTodo())
            }
            resetTodoState()
        }
    }

    suspend fun eliminateTodo() {
        todoRepository.deleteTodo(todoUiState.todoState.toTodo())
    }
}
