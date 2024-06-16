package com.todotrek.feature.todo.mapper

import com.todotrek.domain.model.ToDoItemDomainModel
import com.todotrek.feature.todo.model.AddToDoModel
import com.todotrek.feature.todo.model.ToDoModel

fun ToDoItemDomainModel.toPresentationModel() = ToDoModel(
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun ToDoModel.toDomainModel() = ToDoItemDomainModel(
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)


fun AddToDoModel.toDomainModel() = ToDoItemDomainModel(
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)