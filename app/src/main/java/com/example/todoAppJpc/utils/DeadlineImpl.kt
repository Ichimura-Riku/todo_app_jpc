package com.example.todoAppJpc.utils

import androidx.lifecycle.ViewModel

abstract class DeadlineImpl() : ViewModel() {

    // ---------------- [deadlineState] ----------------
// deadlineUiState
    private var deadlineUiState: DeadlineUiState = DeadlineUiState()
//    by savedStateHandle.saveable {
//    mutableStateOf(DeadlineUiState())
//}


//    // deadline(Picker)State
//    private var _timePickerState: TimePickerState = deadlineUiState.deadlineState.timePickerState
//
//    private var _datePickerState: DatePickerState = deadlineUiState.deadlineState.datePickerState
//
//    val timePickerState: TimePickerState get() = _timePickerState
//    val datePickerState: DatePickerState get() = _datePickerState
//    suspend fun updateDatePickerState(selectedDateMillis: Long?) {
//        try {
//            viewModelScope.async(Dispatchers.IO) {
//                _datePickerState.setSelection(selectedDateMillis)
//            }.await()
//        } catch (e: Exception) {
//            Log.e("error is updateDatePickerState()", "$e")
//        }
//    }
//
//    fun resetTimePickerState() {
//        deadlineUiState.deadlineState.timePickerState = TimePickerState(0, 0, true)
//    }
//
//    fun resetDatePickerState() {
//        deadlineUiState.deadlineState.datePickerState = DatePickerState(
//            initialSelectedDateMillis = Instant.now().toEpochMilli(),
//            initialDisplayedMonthMillis = Instant.now().toEpochMilli(),
//            yearRange = DatePickerDefaults.YearRange,
//            initialDisplayMode = DisplayMode.Picker,
//        )
//    }
//
//    // isInputDeadlineState
//    private var _isInputTimePickerState: Boolean by savedStateHandle.saveable {
//        mutableStateOf(deadlineUiState.isInputDeadlineState.isInputTimePickerState)
//    }
//    private var _isInputDatePickerState: Boolean by savedStateHandle.saveable {
//        mutableStateOf(deadlineUiState.isInputDeadlineState.isInputDatePickerState)
//    }
//
//    val isInputTimePickerState: Boolean get() = _isInputTimePickerState
//    val isInputDatePickerState: Boolean get() = _isInputDatePickerState
//    fun updateIsInputTimePickerState(isInputState: Boolean) {
//        _isInputTimePickerState = isInputState
//    }
//
//    fun updateIsInputDatePickerState(isInputState: Boolean) {
//        _isInputDatePickerState = isInputState
//    }
//
//    val getIsInputDeadlineState: Boolean get() = deadlineUiState.isInputDeadlineState.isInputDatePickerState || deadlineUiState.isInputDeadlineState.isInputTimePickerState
//
//    // deadlineUiState(Uiに直接表示するための値)
//    private val _deadlineUiViewState = MutableStateFlow("")
//    val deadlineUiViewState: StateFlow<String> get() = _deadlineUiViewState
//
//    private suspend fun updateDeadlineUiViewState() {
//        val newUiState = getDeadlineUiViewState() // ここで非同期処理を行う関数を呼び出す
//        Log.d("debug-----", "$newUiState")
//        _deadlineUiViewState.value = newUiState
//        setDeadlineTodoState(deadlineState = deadlineUiState.deadlineState)
//    }
//
//    private suspend fun getDeadlineUiViewState(): String {
//        val userLocale = Locale.getDefault()
//        val cal = Calendar.getInstance()
//
//        val timeFormatter = SimpleDateFormat("HH:mm", userLocale)
//        val timeState = _timePickerState
//        cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
//        cal.set(Calendar.MINUTE, timeState.minute)
//        cal.isLenient = false
//        val timeUiState =
//            if (_isInputTimePickerState) timeFormatter.format(cal.time) else ""
//        val dateFormatter = SimpleDateFormat("MM/dd(EEE)", userLocale)
//
//        try {
//            val setCalApply = viewModelScope.async(Dispatchers.IO) {
//                val dateState = _datePickerState
//                cal.apply {
//                    timeInMillis = dateState.selectedDateMillis!!
//                }
//            }.await()
//            setCalApply
//        } catch (e: Exception) {
//            Log.e("getDeadlineUiState() is error", "$e")
//        }
//
//        val dateUiState =
//            if (_isInputDatePickerState) dateFormatter.format(cal.time) else ""
//
//        return "$dateUiState $timeUiState"
//    }
//
//    private fun setDeadlineTodoState(deadlineState: DeadlineState) {
//        val inputDeadlineTimeHour = 10000 + deadlineState.timePickerState.hour * 100
//        val inputDeadlineTimeMinute = deadlineState.timePickerState.minute
//        val inputDeadlineDate = deadlineState.datePickerState.selectedDateMillis!!
//
//        updateTodoState(
//            todoUiState.todoState.copy(
//                deadlineDate = inputDeadlineDate,
//                deadlineTimeHour = inputDeadlineTimeHour,
//                deadlineTimeMinute = inputDeadlineTimeMinute,
//            )
//        )
//        Log.d("updateTodoState-----", "${todoUiState.todoState.deadlineDate}")
//        Log.d("updateTodoState-----", "${todoUiState.todoState.deadlineTimeHour}")
//        Log.d("updateTodoState-----", "${todoUiState.todoState.deadlineTimeMinute}")
//    }
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
}
