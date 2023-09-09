package com.example.todo_app_jpc.data

import kotlinx.coroutines.flow.Flow

class OfflineTodoRepository(private val todoDao: TodoDao) : TodoRepository{
    override fun getAllTodoStream(): Flow<List<Todo>> = todoDao.getAllTodo()

//    override fun getTodoStream(id: Int): Flow<Todo?> = todoDao.getTodoById(id)

    override suspend fun insertTodo(todo: Todo) = todoDao.insert(todo)

    override suspend fun deleteTodo(todo: Todo) = todoDao.delete(todo)

    override suspend fun updateTodo(todo: Todo) = todoDao.update(todo)

    override suspend fun getAttentionTodo(): Flow<List<Todo?>>  = todoDao.getAttentionTodo()
}