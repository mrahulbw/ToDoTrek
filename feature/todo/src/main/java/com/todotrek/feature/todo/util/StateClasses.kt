package com.todotrek.feature.todo.util

import com.todotrek.feature.todo.model.AddToDoModel

sealed interface ToDoListUiState<out T> {
    data object Loading : ToDoListUiState<Nothing>
    data class Error(val throwable: Throwable) : ToDoListUiState<Nothing>
    data class Success<T>(val data: List<T>) : ToDoListUiState<T>
}

sealed class AddToDoUiState {
    data object Loading : AddToDoUiState()
    data object Error : AddToDoUiState()
    data object Success : AddToDoUiState()
    data object None : AddToDoUiState()
}

sealed class AddToDoAction {
    data class Add(val addToDoModel: AddToDoModel) : AddToDoAction()
    data object AddSuccess : AddToDoAction()
    data object AddError : AddToDoAction()
    data object None : AddToDoAction()
}

sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
    data object Clear : SearchAction()
    data object None : SearchAction()
}