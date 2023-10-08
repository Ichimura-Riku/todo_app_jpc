package com.example.todoAppJpc.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class OfflineTodoRepository(private val todoDao: TodoDao) : TodoRepository {

    override fun getAllTodoStream(): Flow<List<TodoEntity>> =
        todoDao.getAllTodo().flowOn(Dispatchers.IO)

    override fun getAttentionTodo(): Flow<List<TodoEntity>> =
        todoDao.getAttentionTodo().flowOn(Dispatchers.IO)

    override fun getTodoByCategory(category: String): Flow<List<TodoEntity>> =
        todoDao.getTodoByCategory(category).flowOn(Dispatchers.IO)

    override fun getTodoById(id: Int): Flow<TodoEntity> =
        todoDao.getTodoById(id).flowOn(Dispatchers.IO)

    override suspend fun insertTodo(todoEntity: TodoEntity) =
        withContext(Dispatchers.IO) { todoDao.insert(todoEntity) }

    override suspend fun deleteTodo(todoEntity: TodoEntity) =
        withContext(Dispatchers.IO) { todoDao.delete(todoEntity) }

    override suspend fun updateTodo(todoEntity: TodoEntity) =
        withContext(Dispatchers.IO) { todoDao.update(todoEntity) }
}
