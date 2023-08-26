package com.example.todo_app_jpc.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: String,
    val deadLine: String,
    val isAttention: Boolean,
    val category: String,
    val isFinished: Boolean,
    val priority: String,
    )