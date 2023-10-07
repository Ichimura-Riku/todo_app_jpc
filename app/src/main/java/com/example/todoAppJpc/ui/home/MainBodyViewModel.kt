package com.example.todoAppJpc.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.todoAppJpc.data.TodoEntity
import com.example.todoAppJpc.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
@OptIn(SavedStateHandleSaveableApi::class)
class MainBodyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    todoRepository: TodoRepository,
) : ViewModel() {

    /**
     * Holds home ui state. The list of items are retrieved from [ItemsRepository] and mapped to
     * [HomeUiState]
     */
    private var _showBottomSheet by savedStateHandle.saveable() {
        mutableStateOf(false)
    }

    val mainBodyUiState: StateFlow<MainBodyUiState> =
        todoRepository.getAllTodoStream().map { MainBodyUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MainBodyUiState(),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun getShowBottomSheet(): Boolean {
        return _showBottomSheet
    }

    fun setShowBottomSheet(bottomSheetState: Boolean) {
        _showBottomSheet = bottomSheetState
    }
}

/**
 * Ui State for HomeScreen
 */
data class MainBodyUiState(val todoList: List<TodoEntity> = listOf())
