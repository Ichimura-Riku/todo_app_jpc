package com.example.todoAppJpc.di

import com.example.todoAppJpc.data.OfflineTodoRepository
import com.example.todoAppJpc.data.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class TodoRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTodoRepository(offlineTodoRepository: OfflineTodoRepository): TodoRepository

}
