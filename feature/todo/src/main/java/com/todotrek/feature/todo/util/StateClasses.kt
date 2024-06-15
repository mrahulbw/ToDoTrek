package com.todotrek.feature.todo.util

import com.todotrek.feature.todo.model.ToDoModel
import com.todotrek.feature.todo.model.AddToDoModel

sealed interface ToDoListUiState {
    data object Loading : ToDoListUiState
    data class Error(val throwable: Throwable) : ToDoListUiState
    data class Success(val data: List<ToDoModel>) : ToDoListUiState
}

sealed interface AddToDoUiState {
    data object Loading : AddToDoUiState
    data class Error(val throwable: Throwable) : AddToDoUiState
    data class Success(val data: List<AddToDoModel>) : AddToDoUiState
}