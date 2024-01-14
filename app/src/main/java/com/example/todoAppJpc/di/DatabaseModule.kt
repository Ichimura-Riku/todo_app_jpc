package com.example.todoAppJpc.di

import android.content.Context
import androidx.room.Room
import com.example.todoAppJpc.data.TodoDao
import com.example.todoAppJpc.data.TodoListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Volatile
    private var Instance: TodoListDatabase? = null

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TodoListDatabase {
        return Room.databaseBuilder(context, TodoListDatabase::class.java, "todo_database")
            .fallbackToDestructiveMigration()
            .build()
            .also { Instance = it }
    }

    @Provides
    @Singleton
    fun provideTodoDao(todoListDatabase: TodoListDatabase): TodoDao {
        return todoListDatabase.todoDao()
    }
}