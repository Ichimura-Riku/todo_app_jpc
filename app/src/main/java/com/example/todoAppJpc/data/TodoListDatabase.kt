package com.example.todoAppJpc.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 2, exportSchema = false)
abstract class TodoListDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
//    @Volatile
//    private var Instance: TodoListDatabase? = null

//    fun getDatabase(context: Context): TodoListDatabase {
//        // if the Instance is not null, return it, otherwise create a new database instance.
//        return Instance ?: synchronized(this) {
//            Room.databaseBuilder(context, TodoListDatabase::class.java, "todo_database")
//                .fallbackToDestructiveMigration()
//                .build()
//                .also { Instance = it }
//        }
//    }

}
