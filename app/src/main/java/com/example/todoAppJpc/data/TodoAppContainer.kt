package com.example.todoAppJpc.data


// Hiltのおかげでファイル自体が入らなくなる

interface TodoAppContainer {
    val todoRepository: TodoRepository
}

//class TodoAppDataContainer(private val context: Context) : TodoAppContainer {
//    override val todoRepository: TodoRepository by lazy {
//        OfflineTodoRepository(TodoListDatabase.getDatabase(context).todoDao())
//    }
//}
