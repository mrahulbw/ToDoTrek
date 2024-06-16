package com.todotrek.domain.usecases.testhelpers

import com.todotrek.domain.model.ToDoItemDomainModel
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeGetToDoListUseCase {
    private val fakeFlow = MutableSharedFlow<Result<List<ToDoItemDomainModel>>>()

    suspend fun emit(value: Result<List<ToDoItemDomainModel>>) = fakeFlow.emit(value)

    fun fetch() = fakeFlow
}