package com.todotrek.domain.model

data class ToDoDomainModel(
    val toDoList: List<ToDoItemDomainModel>
)

data class ToDoItemDomainModel(
    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)