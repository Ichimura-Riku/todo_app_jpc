package com.example.todoAppJpc.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoAppJpc.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository,
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[TodoEditDestination.todoIdArg])
    var todoUiState by mutableStateOf(TodoUiState())
        private set

    init {
        viewModelScope.launch {
            todoUiState = todoRepository.getTodoById(itemId)
                .filterNotNull()
                .first()
                .toTodoUiState()
        }
    }

    fun updateTodoState(todoState: TodoState) {
        todoUiState = TodoUiState(todoState = todoState)
    }

    suspend fun adventTodo() {

        todoRepository.updateTodo(todoUiState.todoState.toTodo())
//        Log.d( "debug", "↓デバッグ")
//        Log.d( "debug", OnConflictStrategy.IGNORE.toString())
    }

    suspend fun eliminateTodo() {
        todoRepository.deleteTodo(todoUiState.todoState.toTodo())
    }

}

