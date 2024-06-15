package com.todotrek.domain.usecases

import com.todotrek.domain.model.ToDoItemDomainModel
import com.todotrek.domain.repository.ToDoRepository
import javax.inject.Inject

class AddToDoUseCase @Inject constructor(
    private val toDoRepository: ToDoRepository
) {
    suspend operator fun invoke(newToDo: ToDoItemDomainModel) {
        toDoRepository.addToDo(newToDo)
    }
}