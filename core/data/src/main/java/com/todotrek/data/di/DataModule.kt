package com.todotrek.data.di

import com.todotrek.data.repository.ToDoRepositoryImpl
import com.todotrek.domain.repository.ToDoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindToDoRepository(toDoRepositoryImpl: ToDoRepositoryImpl): ToDoRepository
}