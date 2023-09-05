package com.example.todo_app_jpc.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_app_jpc.Data.Todo
import com.example.todo_app_jpc.Data.TodoRepository
import com.example.todo_app_jpc.ItemData.Item
import com.example.todo_app_jpc.ItemData.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class MainBodyViewModel(todoRepository: TodoRepository) : ViewModel() {

    /**
     * Holds home ui state. The list of items are retrieved from [ItemsRepository] and mapped to
     * [HomeUiState]
     */
    val homeUiState: StateFlow<MainBodyUiState> =
        todoRepository.getAllTodoStream().map { MainBodyUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MainBodyUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class MainBodyUiState(val itemList: List<Todo> = listOf())
