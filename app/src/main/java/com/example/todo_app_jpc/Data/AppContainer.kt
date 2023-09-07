package com.example.todo_app_jpc.Data

import android.content.Context


interface AppContainer {
    val todoRepository: TodoRepository
}
class TodoAppDataContainer(private val context: Context): AppContainer {
    override val todoRepository: TodoRepository by lazy {
        OfflineTodosRepository(TodoListDatabase.getDatabase(context).todoDao())
    }
}