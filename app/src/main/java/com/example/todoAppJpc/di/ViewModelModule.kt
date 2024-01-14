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
//class TodoRepositoryModule {
abstract class TodoRepositoryModule {
    //    @Provides
//    @Singleton
////    fun provideTodoRepository(@ApplicationContext context: Context): TodoRepository {
//    fun provideTodoRepository(@ApplicationContext context: Context): TodoRepository {
//        return OfflineTodoRepository(TodoListDatabase.getDatabase(context = context).todoDao())
//    }
    @Binds
    @Singleton
    abstract fun bindTodoRepository(offlineTodoRepository: OfflineTodoRepository): TodoRepository

}
