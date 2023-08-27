package com.example.todo_app_jpc.Data

import android.content.Context


interface AppContainer {
    val todoRepository: TodoRepository
}
class AppDataContainer(private val context: Context): AppContainer {
    override val todoRepository: TodoRepository by lazy {
        OfflineTodosRepository(InventoryDatabase.getDatabase(context).todoDao())
    }
}