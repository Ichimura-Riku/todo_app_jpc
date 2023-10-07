package com.example.todoAppJpc.ui.todo

import androidx.lifecycle.ViewModel
import com.example.todoAppJpc.data.TodoEntity
import com.example.todoAppJpc.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {
//    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])
}

data class TodoDetailsUiState(
    val todoDetail: TodoEntity,
)
