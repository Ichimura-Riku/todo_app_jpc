package com.example.todoAppJpc.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.todoAppJpc.MainApplication

//object TodoAppViewModelProvider {
//    val Factory = viewModelFactory {
//        initializer {
//            TodoEntryViewModel(
//                this.createSavedStateHandle(),
//                todoApplication().todoContainer.todoRepository,
//            )
//        }
//        initializer {
//            MainBodyViewModel(
//                this.createSavedStateHandle(),
//                todoApplication().todoContainer.todoRepository,
//            )
//        }
//        initializer {
//            TodoEditViewModel(
//                this.createSavedStateHandle(),
//                todoApplication().todoContainer.todoRepository,
//            )
//        }
//    }
//}

fun CreationExtras.todoApplication(): MainApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
