package com.example.todo_app_jpc.ui.todo

import androidx.lifecycle.ViewModel
import com.example.todo_app_jpc.data.TodoEntity
import com.example.todo_app_jpc.data.TodoRepository

class TodoDetailViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {
//    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

}

data class TodoDetailsUiState(
    val todoDetail: TodoEntity
)