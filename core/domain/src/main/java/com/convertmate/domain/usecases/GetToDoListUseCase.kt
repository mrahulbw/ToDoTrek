package com.todotrek.domain.usecases

import com.todotrek.domain.model.ToDoItemDomainModel
import com.todotrek.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetToDoListUseCase @Inject constructor(
    private val toDoRepository: ToDoRepository
) {
    operator fun invoke(): Flow<Result<List<ToDoItemDomainModel>>> =
        toDoRepository.getToDoList().map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it)) // also catch does re-throw CancellationException
        }
}