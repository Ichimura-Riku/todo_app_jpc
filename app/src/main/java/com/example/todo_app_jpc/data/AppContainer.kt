package com.example.todo_app_jpc.data

import android.content.Context



interface TodoAppContainer {
    val todoRepository: TodoRepository
}
class TodoAppDataContainer(private val context: Context): TodoAppContainer {
    override val todoRepository: TodoRepository by lazy {
        OfflineTodoRepository(TodoListDatabase.getDatabase(context).todoDao())
    }
}