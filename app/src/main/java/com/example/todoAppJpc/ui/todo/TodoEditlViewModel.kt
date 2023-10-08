package com.example.todoAppJpc.ui.todo

import androidx.lifecycle.ViewModel
import com.example.todoAppJpc.data.TodoEntity
import com.example.todoAppJpc.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {
//    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])
}

data class TodoEditUiState(
    val todoEdit: TodoEntity,
)
