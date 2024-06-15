package com.todotrek.feature.todo.model

data class ToDoModel(
    val title: String,
    val description: String,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)