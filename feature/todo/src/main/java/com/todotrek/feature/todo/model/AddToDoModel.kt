package com.todotrek.feature.todo.model

data class AddToDoModel(
    val title: String,
    val description: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)