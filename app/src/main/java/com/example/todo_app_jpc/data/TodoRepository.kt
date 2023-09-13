package com.example.todo_app_jpc.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getAllTodoStream(): Flow<List<TodoEntity>>

    fun getAttentionTodo(): Flow<List<TodoEntity>>

    fun getTodoByCategory(category: String): Flow<List<TodoEntity>>


    suspend fun insertTodo(todoEntity: TodoEntity)

    suspend fun deleteTodo(todoEntity: TodoEntity)

    suspend fun updateTodo(todoEntity: TodoEntity)

}