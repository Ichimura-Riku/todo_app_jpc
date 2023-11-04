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
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "content") val content: String = "",
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "deadline") val deadline: Long = -1000000000000,
    @ColumnInfo(name = "is_attention") val isAttention: Int = 0,
    @ColumnInfo(name = "category") val category: String = "",
    @ColumnInfo(name = "is_finished") val isFinished: Int = 0,
    @ColumnInfo(name = "priority") val priority: String = "", // 重要度
)
