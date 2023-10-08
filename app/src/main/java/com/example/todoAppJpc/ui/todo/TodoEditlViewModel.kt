package com.example.todoAppJpc.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todoAppJpc.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {
    //    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])
    var todoUiState by mutableStateOf(TodoUiState())
        private set

    fun updateTodoState(todoState: TodoState) {
        todoUiState = TodoUiState(todoState = todoState)
    }

    suspend fun adventTodo() {
        todoRepository.insertTodo(todoUiState.todoState.toTodo())
    }

}

