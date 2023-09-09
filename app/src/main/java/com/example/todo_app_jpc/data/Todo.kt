package com.example.todo_app_jpc.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: String,
    val deadLine: String,
    val isAttention: Int,
    val category: String,
    val isFinished: Int,
    val priority: String,
    )