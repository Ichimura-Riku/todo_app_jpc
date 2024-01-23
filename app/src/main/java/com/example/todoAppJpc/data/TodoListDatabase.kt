package com.example.todoAppJpc.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 2, exportSchema = false)
abstract class TodoListDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}
