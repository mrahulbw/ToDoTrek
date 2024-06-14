package com.todotrek.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoEntity(
    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}