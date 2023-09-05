package com.example.todo_app_jpc.ui
//
//import android.text.Editable.Factory
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
//import androidx.lifecycle.createSavedStateHandle
//import androidx.lifecycle.viewmodel.CreationExtras
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.example.todo_app_jpc.MainApplication
//
//import com.example.todo_app_jpc.ui.todo.TodoEntryViewModel
//import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.ViewModelFactoriesEntryPoint
//
//object AppViewModelProvider {
//    val Factory = viewModelFactory {
//        initializer {
//            TodoEntryViewModel(mainApplication().container.todoRepository)
//        }
//    }
////    val Factory = viewModelFactory {
////        // Initializer for ItemEditViewModel
////        initializer {
////            TodoEditViewModel(
////                this.createSavedStateHandle(),
////                inventoryApplication().container.itemsRepository
////            )
////        }
////        // Initializer for ItemEntryViewModel
////        initializer {
////            TodoEntryViewModel(mainApplication().container.todoRepository)
////        }
////
////        // Initializer for ItemDetailsViewModel
////        initializer {
////            TodoDetailsViewModel(
////                this.createSavedStateHandle(),
////                inventoryApplication().container.itemsRepository
////            )
////        }
////
////        // Initializer for HomeViewModel
////        initializer {
////            HomeViewModel(inventoryApplication().container.itemsRepository)
////        }
////    }
//}
//fun CreationExtras.mainApplication() : MainApplication =
//    (this[AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)


/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo_app_jpc.MainApplication
import com.example.todo_app_jpc.ui.home.HomeViewModel
import com.example.todo_app_jpc.ui.item.ItemDetailsViewModel
import com.example.todo_app_jpc.ui.item.ItemEditViewModel
//import com.example.inventory.InventoryApplication
//import com.example.inventory.ui.home.HomeViewModel
//import com.example.inventory.ui.item.ItemDetailsViewModel
//import com.example.inventory.ui.item.ItemEditViewModel
//import com.example.inventory.ui.item.ItemEntryViewModel
import com.example.todo_app_jpc.ui.item.ItemEntryViewModel

///**
// * Provides Factory to create instance of ViewModel for the entire Inventory app
// */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(inventoryApplication().container.itemsRepository)
        }
    }
}

///**
// * Extension function to queries for [Application] object and returns an instance of
// * [InventoryApplication].
// */
fun CreationExtras.inventoryApplication(): MainApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
