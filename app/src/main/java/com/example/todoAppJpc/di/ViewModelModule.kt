package com.example.todoAppJpc.di

import android.content.Context
import com.example.todoAppJpc.data.OfflineTodoRepository
import com.example.todoAppJpc.data.TodoListDatabase
import com.example.todoAppJpc.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideViewModel(@ApplicationContext context: Context): TodoRepository {
        return OfflineTodoRepository(TodoListDatabase.getDatabase(context = context).todoDao())
    }
}
