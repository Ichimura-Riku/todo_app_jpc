package com.example.todoAppJpc.ui.todo

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.todoAppJpc.data.TodoEntity
import com.example.todoAppJpc.data.TodoRepository
import com.example.todoAppJpc.utils.DeadlineState
import com.example.todoAppJpc.utils.DeadlineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class, SavedStateHandleSaveableApi::class)
@HiltViewModel
class TodoEntryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository,
) : ViewModel() {
    var todoUiState by mutableStateOf(TodoUiState())
        private set

    // ---------------- [contextTextField] ----------------
    private var _showContentTextField by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    fun getShowContentTextField(): Boolean {
        return _showContentTextField
    }

    fun setShowContentTextField(showContentTextField: Boolean) {
        _showContentTextField = showContentTextField
    }

    // ---------------- [deadlineState] ----------------
    // deadlineUiState
    private var _deadlineUiState: DeadlineUiState by savedStateHandle.saveable {
        mutableStateOf(DeadlineUiState())
    }

    val deadlineUiState: DeadlineUiState get() = _deadlineUiState

    private var _deadlineUiViewState: MutableStateFlow<String> = MutableStateFlow("")

    val deadlineUiViewState: StateFlow<String> get() = _deadlineUiViewState


    suspend fun updateDeadlineUiViewState() {
        val newUiState = getDeadlineUiViewState() // ここで非同期処理を行う関数を呼び出す
        _deadlineUiViewState.value = newUiState
        setDeadlineTodoState(deadlineState = deadlineUiState.deadlineState)
    }

    private suspend fun getDeadlineUiViewState(): String {
        val userLocale = Locale.getDefault()
        val cal = Calendar.getInstance()

        val timeFormatter = SimpleDateFormat("HH:mm", userLocale)
        val timeState = _deadlineUiState.deadlineState.timePickerState
        cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
        cal.set(Calendar.MINUTE, timeState.minute)
        cal.isLenient = false
        val timeUiState =
            if (_deadlineUiState.isInputTimePickerState) timeFormatter.format(cal.time) else ""
        val dateFormatter = SimpleDateFormat("MM/dd(EEE)", userLocale)

        try {
            val setCalApply = viewModelScope.async(Dispatchers.IO) {
                val dateState = _deadlineUiState.deadlineState.datePickerState
                cal.apply {
                    timeInMillis = dateState.selectedDateMillis!!
                }
            }.await()
            setCalApply
        } catch (e: Exception) {
            Log.e("getDeadlineUiState() is error", "$e")
        }

        val dateUiState =
            if (_deadlineUiState.isInputDatePickerState) dateFormatter.format(cal.time) else ""

        return "$dateUiState $timeUiState"
    }

    private fun setDeadlineTodoState(deadlineState: DeadlineState) {

        Log.d("debug-----", "dateUpdateTodoState")
        val inputDeadlineTimeHour = 10000 + deadlineState.timePickerState.hour * 100
        Log.d("debug-----", "dateUpdateTodoState")
        val inputDeadlineTimeMinute = deadlineState.timePickerState.minute
        Log.d("debug-----", "dateUpdateTodoState")
        val inputDeadlineDate = deadlineState.datePickerState.selectedDateMillis!!

        Log.d("debug-----", "dateUpdateTodoState")

        updateTodoState(
            todoUiState.todoState.copy(
                deadlineDate = inputDeadlineDate,
                deadlineTimeHour = inputDeadlineTimeHour,
                deadlineTimeMinute = inputDeadlineTimeMinute,
            )
        )
        Log.d("updateTodoState-----", "${todoUiState.todoState.deadlineDate}")
        Log.d("updateTodoState-----", "${todoUiState.todoState.deadlineTimeHour}")
        Log.d("updateTodoState-----", "${todoUiState.todoState.deadlineTimeMinute}")
    }


    fun updateTodoState(todoState: TodoState) {
        todoUiState = TodoUiState(todoState = todoState)
    }

    //   　fun adventTodo() {　
    suspend fun adventTodo() {
        //  viewModelScope.launch{}
        todoRepository.insertTodo(todoUiState.todoState.toTodo())
    }
}

data class TodoUiState(
    val todoState: TodoState = TodoState(),

    )

// ItemDetailsの代わり
data class TodoState(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val date: String = "",
    var deadlineDate: Long = -1000000000000,
    val deadlineTimeHour: Int = 10000,
    val deadlineTimeMinute: Int = 100,
    val isAttention: Int = 0,
    val category: String = "myTask",
    val isFinished: Int = 0,
    val priority: String = "low",
)

fun TodoState.toTodo(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    content = content,
//    多分日時系は型変換する必要がある
    date = date,
    deadline = deadlineDate + deadlineTimeHour + deadlineTimeMinute % 100,
    isAttention = isAttention,
    category = category,
    isFinished = isFinished,
    priority = priority,
)

fun TodoEntity.toTodoUiState(): TodoUiState = TodoUiState(
    todoState = this.toTodoState(),
)

fun TodoEntity.toTodoState(): TodoState = TodoState(
    id = id,
    title = title,
    content = content,
//    多分日時系は型変換する必要がある
    date = date,
    deadlineDate = deadline / 100000,
    deadlineTimeHour = ((deadline % 100000) / 100).toInt(),
    deadlineTimeMinute = (deadline % 100).toInt(),
    isAttention = isAttention,
    category = category,
    isFinished = isFinished,
    priority = priority,
)


//
//    // ---------------- [showDatePicker] ----------------
//    private var _showDatePicker by savedStateHandle.saveable {
//        mutableStateOf(false)
//    }
//
//    fun getShowDatePicker(): Boolean {
//        return _showDatePicker
//    }
//
//    fun setShowDatePicker(showDatePicker: Boolean) {
//        _showDatePicker = showDatePicker
//    }
//
//    // ---------------- [showTimePicker] ----------------
//    private var _showTimePicker by savedStateHandle.saveable {
//        mutableStateOf(false)
//    }
//
//    fun getShowTimePicker(): Boolean {
//        return _showTimePicker
//    }
//
//    fun setShowTimePicker(showTimePicker: Boolean) {
//        _showTimePicker = showTimePicker
//    }