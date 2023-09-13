package com.example.todo_app_jpc.ui

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo_app_jpc.MainApplication
import com.example.todo_app_jpc.ui.home.MainBodyViewModel
import com.example.todo_app_jpc.ui.todo.TodoEntryViewModel

object TodoAppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodoEntryViewModel(
                todoApplication().todoContainer.todoRepository
            )
        }
        initializer {
            MainBodyViewModel(
                todoApplication().todoContainer.todoRepository
            )
        }
    }
}

fun CreationExtras.todoApplication(): MainApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)