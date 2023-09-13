package com.example.todo_app_jpc.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todo_app_jpc.data.TodoEntity
import com.example.todo_app_jpc.data.TodoRepository

class TodoEntryViewModel (private val todoRepository: TodoRepository): ViewModel(){
    var todoUiState by mutableStateOf(TodoUiState())
        private set
    fun updateTodoState(todoState: TodoState){
        todoUiState = TodoUiState(todoState = todoState )
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
){
//    あえて違う実装にしてみる
    fun toTodo(): TodoEntity = TodoEntity(
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
}

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

