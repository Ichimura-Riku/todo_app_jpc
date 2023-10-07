package com.example.todoAppJpc.data

import android.content.Context

interface TodoAppContainer {
    val todoRepository: TodoRepository
}

class TodoAppDataContainer(private val context: Context) : TodoAppContainer {
    override val todoRepository: TodoRepository by lazy {
        OfflineTodoRepository(TodoListDatabase.getDatabase(context).todoDao())
    }
}
