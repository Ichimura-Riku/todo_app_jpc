package com.example.todo_app_jpc.di

import android.content.Context
import com.example.todo_app_jpc.data.OfflineTodoRepository
import com.example.todo_app_jpc.data.TodoListDatabase
import com.example.todo_app_jpc.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Module {
    @Provides
    @Singleton
    fun provideViewModel(@ApplicationContext context: Context): TodoRepository{
        return OfflineTodoRepository(TodoListDatabase.getDatabase(context = context).todoDao())
    }
}