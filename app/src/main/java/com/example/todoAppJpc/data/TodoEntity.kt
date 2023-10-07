package com.example.todoAppJpc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "deadline") val deadLine: String,
    @ColumnInfo(name = "is_attention") val isAttention: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "is_finished") val isFinished: Int,
    @ColumnInfo(name = "priority") val priority: String,
)
