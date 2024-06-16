package com.todotrek.feature.todo.util

import com.todotrek.feature.todo.model.ToDoModel
import com.todotrek.feature.todo.model.AddToDoModel

sealed interface ToDoListUiState {
    data object Loading : ToDoListUiState
    data class Error(val throwable: Throwable) : ToDoListUiState
    data class Success(val data: List<ToDoModel>) : ToDoListUiState
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