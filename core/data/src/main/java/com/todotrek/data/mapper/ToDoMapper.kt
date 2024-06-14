package com.todotrek.data.mapper

import com.todotrek.database.ToDoEntity
import com.todotrek.domain.model.ToDoItemDomainModel

fun ToDoEntity.toDomainItemModel() = ToDoItemDomainModel(
    title = this.title,
    description = this.description,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun ToDoItemDomainModel.toEntityModel() = ToDoEntity(
    title = this.title,
    description = this.description,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)