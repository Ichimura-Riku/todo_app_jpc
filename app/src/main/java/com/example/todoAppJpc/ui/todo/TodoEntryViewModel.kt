package com.example.todoAppJpc.ui.todo

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.todoAppJpc.data.TodoEntity
import com.example.todoAppJpc.data.TodoRepository
import com.example.todoAppJpc.utils.DeadlineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.time.Instant
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
    private var deadlineUiState: DeadlineUiState by savedStateHandle.saveable {
        mutableStateOf(DeadlineUiState())
    }

    // deadline(Picker)State
    private var _timePickerState: TimePickerState by savedStateHandle.saveable {
        mutableStateOf(deadlineUiState.deadlineState.timePickerState)
    }
    private var _datePickerState: DatePickerState by savedStateHandle.saveable {
        mutableStateOf(deadlineUiState.deadlineState.datePickerState)
    }
    val timePickerState: TimePickerState get() = _timePickerState
    val datePickerState: DatePickerState get() = _datePickerState
    fun updateDatePickerState(selectedDateMillis: Long?) {
        _datePickerState.setSelection(selectedDateMillis)
    }

    fun resetTimePickerState() {
        deadlineUiState.deadlineState.timePickerState = TimePickerState(0, 0, true)
    }

    fun resetDatePickerState() {
        deadlineUiState.deadlineState.datePickerState = DatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli(),
            initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker,
        )
    }

    // isInputDeadlineState
    private var _isInputTimePickerState: Boolean by savedStateHandle.saveable {
        mutableStateOf(deadlineUiState.isInputDeadlineState.isInputTimePickerState)
    }
    private var _isInputDatePickerState: Boolean by savedStateHandle.saveable {
        mutableStateOf(deadlineUiState.isInputDeadlineState.isInputDatePickerState)
    }
    val isInputTimePickerState: Boolean get() = _isInputTimePickerState
    val isInputDatePickerState: Boolean get() = _isInputDatePickerState
    fun updateIsInputTimePickerState(isInputState: Boolean) {
        _isInputTimePickerState = isInputState
    }

    fun updateIsInputDatePickerState(isInputState: Boolean) {
        _isInputDatePickerState = isInputState
    }

    val getIsInputDeadlineState: Boolean get() = deadlineUiState.isInputDeadlineState.isInputDatePickerState || deadlineUiState.isInputDeadlineState.isInputTimePickerState

    // deadlineUiState(Uiに直接表示するための値)
    fun getDeadlineUiState(): String {
        val userLocale = Locale.getDefault()
        val cal = Calendar.getInstance()

        val timeFormatter = SimpleDateFormat("HH:mm", userLocale)
        val timeState = _timePickerState
        cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
        cal.set(Calendar.MINUTE, timeState.minute)
        cal.isLenient = false
        val timeUiState =
            if (_isInputTimePickerState) timeFormatter.format(cal.time) else ""

        val dateFormatter = SimpleDateFormat("MM/dd(EEE)", userLocale)
        val dateState = _datePickerState
        cal.apply {
            // あとで、コルーチン処理かなんかでawaitさせる
            timeInMillis = dateState.selectedDateMillis ?: Instant.now().toEpochMilli()
//            timeInMillis = dateState.selectedDateMillis!!
        }
        val dateUiState =
            if (_isInputDatePickerState) dateFormatter.format(cal.time) else ""

        return "$dateUiState $timeUiState"
    }

    // ---------------- [showDatePicker] ----------------
    private var _showDatePicker by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    fun getShowDatePicker(): Boolean {
        return _showDatePicker
    }

    fun setShowDatePicker(showDatePicker: Boolean) {
        _showDatePicker = showDatePicker
    }

    // ---------------- [showTimePicker] ----------------
    private var _showTimePicker by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    fun getShowTimePicker(): Boolean {
        return _showTimePicker
    }

    fun setShowTimePicker(showTimePicker: Boolean) {
        _showTimePicker = showTimePicker
    }

    fun updateTodoState(todoState: TodoState) {
        todoUiState = TodoUiState(todoState = todoState)
    }

    suspend fun adventTodo() {
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
    val deadLine: String = "",
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
    deadLine = deadLine,
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
    deadLine = deadLine,
    isAttention = isAttention,
    category = category,
    isFinished = isFinished,
    priority = priority,
)
