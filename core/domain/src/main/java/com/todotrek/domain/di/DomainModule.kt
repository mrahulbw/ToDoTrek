package com.todotrek.domain.di

import com.todotrek.domain.usecases.AddToDoUseCase
import com.todotrek.domain.usecases.GetToDoListUseCase
import com.todotrek.domain.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainModule {
    @Provides
    fun provideGetToDoListUseCase(toDoRepository: ToDoRepository) =
        GetToDoListUseCase(toDoRepository)

    @Provides
    fun provideAddToDoUseCase(toDoRepository: ToDoRepository) =
        AddToDoUseCase(toDoRepository)
}