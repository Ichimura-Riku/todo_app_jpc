package com.example.todo_app_jpc.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todo_app_jpc.data.TodoEntity
import com.example.todo_app_jpc.data.TodoRepository

//class TodoEntryViewModel(
//    private val todoRepository: TodoRepository) : ViewModel() {
//    var todoUiState by mutableStateOf(TodoUiState())
//
//    fun updateUiState(todoDetails: TodoDetails) {
//        todoUiState =
//            TodoUiState(todoDetails = todoDetails, isEntryValid = validateInput(todoDetails))
//    }
//
//    suspend fun saveTodo(){
//        if (validateInput()) {
//            todoRepository.insertTodo(todoUiState.todoDetails.toTodo())
//        }
//    }
//    // 書くカラムが空かどうかを判定する
//    private fun validateInput(uiState: TodoDetails = todoUiState.todoDetails): Boolean {
//        return with(uiState) {
//            title.isNotBlank() && content.isNotBlank() && date.isNotBlank() && deadLine.isNotBlank() && isAttention.isNotBlank() && category.isNotBlank() && isFinished.isNotBlank() && priority.isNotBlank()
//        }
//    }
//
//}
//
//data class TodoUiState(
//    val todoDetails: TodoDetails = TodoDetails(),
//    val isEntryValid: Boolean = false
//)
//
//data class TodoDetails(
//    val id: Int = 0,
//    val title: String = "",
//    val content: String = "",
//    val date: String = "",
//    val deadLine: String = "",
//    val isAttention: String = "false",
//    val category: String = "",
//    val isFinished: String = "false",
//    val priority: String = "",
//)
//
//// database -> VM
//fun TodoDetails.toTodo(): Todo = Todo(
//    id = id,
//    title = title,
//    content = content,
//    date = date,
//    deadLine = deadLine,
//    isAttention = isAttention.toBooleanStrict(),
//    category = category,
//    isFinished = isFinished.toBooleanStrict(),
//    priority = priority,
//)
//
//// TODO: parseが必要な値を定義する
//@RequiresApi(Build.VERSION_CODES.O)
//fun Todo.formatedDate(): LocalDateTime{
//    return LocalDateTime.parse(deadLine, DateTimeFormatter.ISO_DATE)
//}
//
//fun Todo.toTodoUiState(isEntryValid: Boolean = false): TodoUiState = TodoUiState(
//    todoDetails = this.toTodoDetails(),
//    isEntryValid = isEntryValid
//)
//
//// VM -> database
//fun Todo.toTodoDetails(): TodoDetails = TodoDetails(
//    id = id,
//    title = title,
//    content = content,
//    date = date,
//    deadLine = deadLine,
//    isAttention = isAttention.toString(),
//    category = category,
//    isFinished = isFinished.toString(),
//    priority = priority,
//)

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

