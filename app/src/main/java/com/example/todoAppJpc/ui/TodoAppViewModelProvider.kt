package com.example.todoAppJpc.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoAppJpc.MainApplication
import com.example.todoAppJpc.ui.home.MainBodyViewModel
import com.example.todoAppJpc.ui.todo.TodoEditViewModel
import com.example.todoAppJpc.ui.todo.TodoEntryViewModel

object TodoAppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodoEntryViewModel(
                this.createSavedStateHandle(),
                todoApplication().todoContainer.todoRepository,
            )
        }
        initializer {
            MainBodyViewModel(
                this.createSavedStateHandle(),
                todoApplication().todoContainer.todoRepository,
            )
        }
        initializer {
            TodoEditViewModel(
                this.createSavedStateHandle(),
                todoApplication().todoContainer.todoRepository,
            )
        }
    }
}

fun CreationExtras.todoApplication(): MainApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
