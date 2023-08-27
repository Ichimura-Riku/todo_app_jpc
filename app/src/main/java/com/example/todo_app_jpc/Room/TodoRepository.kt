package com.example.todo_app_jpc.Room

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    /**
     * Retrieve all the Todos from the the given data source.
     */
    fun getAllTodoStream(): Flow<List<Todo>>

    /**
     * Retrieve an Todo from the given data source that matches with the [id].
     */
    fun getTodoStream(id: Int): Flow<Todo?>

    /**
     * Insert Todo in the data source
     */
    suspend fun insertTodo(todo: Todo)

    /**
     * Delete Todo from the data source
     */
    suspend fun deleteTodo(todo: Todo)

    /**
     * Update Todo in the data source
     */
    suspend fun updateTodo(todo: Todo)
}