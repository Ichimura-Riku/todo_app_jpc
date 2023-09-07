package com.example.todo_app_jpc.ui



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
import com.example.todo_app_jpc.ui.item.ItemEntryViewModel

///**
// * Provides Factory to create instance of ViewModel for the entire Inventory app
// */
//hiltに移行したらこれいらない
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
