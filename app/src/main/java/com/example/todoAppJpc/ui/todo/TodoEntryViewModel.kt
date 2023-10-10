package com.example.todoAppJpc.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.todoAppJpc.data.TodoEntity
import com.example.todoAppJpc.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoEntryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository,
) : ViewModel() {
    var todoUiState by mutableStateOf(TodoUiState())
        private set

    private var _showContentTextField by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    fun getShowContentTextField(): Boolean {
        return _showContentTextField
    }

    fun setShowContentTextField(showContentTextField: Boolean) {
        _showContentTextField = showContentTextField
    }

    fun updateTodoState(todoState: TodoState) {
        todoUiState = TodoUiState(todoState = todoState)
    }

    suspend fun adventTodo() {
        todoRepository.insertTodo(todoUiState.todoState.toTodo())
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
    val deadLine: String = "",
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
    deadLine = deadLine,
    isAttention = isAttention,
    category = category,
    isFinished = isFinished,
    priority = priority,
)


fun TodoEntity.toTodoUiState(): TodoUiState = TodoUiState(
    todoState = this.toTodoState()
)

fun TodoEntity.toTodoState(): TodoState = TodoState(
    id = id,
    title = title,
    content = content,
//    多分日時系は型変換する必要がある
    date = date,
    deadLine = deadLine,
    isAttention = isAttention,
    category = category,
    isFinished = isFinished,
    priority = priority,
)
