//package com.example.todo_app_jpc.ui.todo
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import com.example.todo_app_jpc.Data.TodoRepository
//
//class TodoEditViewModel(
//    savedStateHandle: SavedStateHandle,
//    private val itemsRepository: TodoRepository
//) : ViewModel() {
//
//    /**
//     * Holds current item ui state
//     */
//    var todoUiState by mutableStateOf(TodoUiState())
//        private set
//
//    private val itemId: Int = checkNotNull(savedStateHandle[TodoEditDestination.itemIdArg])
//
//    init {
//        viewModelScope.launch {
//            itemUiState = itemsRepository.getTodoStream(itemId)
//                .filterNotNull()
//                .first()
//                .toTodoUiState(true)
//        }
//    }
//
//    /**
//     * Update the item in the [TodoRepository]'s data source
//     */
//    suspend fun updateTodo() {
//        if (validateInput(itemUiState.itemDetails)) {
//            itemsRepository.updateTodo(itemUiState.itemDetails.toTodo())
//        }
//    }
//
//    /**
//     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
//     * a validation for input values.
//     */
//    fun updateUiState(itemDetails: TodoDetails) {
//        itemUiState =
//            TodoUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
//    }
//
//    private fun validateInput(uiState: TodoDetails = itemUiState.itemDetails): Boolean {
//        return with(uiState) {
//            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
//        }
//    }
//}
