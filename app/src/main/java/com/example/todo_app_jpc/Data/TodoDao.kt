package com.example.todo_app_jpc.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo:Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * from todo ORDER BY deadLine")
    fun getAllTodo(): Flow<List<Todo>>

    @Query("SELECT * from items WHERE id = :id")
    fun getTodoById(id: Int): Flow<Todo?>
}