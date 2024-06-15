package com.todotrek.domain.repository

import com.todotrek.domain.model.ToDoItemDomainModel
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun getToDoList(): Flow<List<ToDoItemDomainModel>>
    suspend fun addToDo(newToDo: ToDoItemDomainModel)
}