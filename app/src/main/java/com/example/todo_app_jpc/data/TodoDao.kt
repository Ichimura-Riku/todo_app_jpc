package com.example.todo_app_jpc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {
    @Query("SELECT * from todo ORDER BY deadLine")
    fun getAllTodo(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE is_attention = 1")
    fun getAttentionTodo(): Flow<List<TodoEntity>>

    @Query("SELECT * from todo WHERE id = :category")
    fun getTodoByCategory(category: String): Flow<List<TodoEntity>>

//    競合が発生した時、その処理を無視するように指定
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoEntity: TodoEntity)

    @Update
    suspend fun update(todoEntity:TodoEntity)

    @Delete
    suspend fun delete(todoEntity: TodoEntity)


}