package com.example.todo_app_jpc.ui

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo_app_jpc.MainApplication

import com.example.todo_app_jpc.ui.todo.TodoEntryViewModel
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.ViewModelFactoriesEntryPoint

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodoEntryViewModel(mainApplication().container.todoRepository)
        }
    }
//    val Factory = viewModelFactory {
//        // Initializer for ItemEditViewModel
//        initializer {
//            TodoEditViewModel(
//                this.createSavedStateHandle(),
//                inventoryApplication().container.itemsRepository
//            )
//        }
//        // Initializer for ItemEntryViewModel
//        initializer {
//            TodoEntryViewModel(mainApplication().container.todoRepository)
//        }
//
//        // Initializer for ItemDetailsViewModel
//        initializer {
//            TodoDetailsViewModel(
//                this.createSavedStateHandle(),
//                inventoryApplication().container.itemsRepository
//            )
//        }
//
//        // Initializer for HomeViewModel
//        initializer {
//            HomeViewModel(inventoryApplication().container.itemsRepository)
//        }
//    }
}
fun CreationExtras.mainApplication() : MainApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)