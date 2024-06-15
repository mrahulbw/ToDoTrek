package com.todotrek.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todotrek.feature.todo.mapper.toPresentationModel
import com.todotrek.feature.todo.model.AddToDoModel
import com.todotrek.domain.usecases.AddToDoUseCase
import com.todotrek.domain.usecases.GetToDoListUseCase
import com.todotrek.feature.todo.mapper.toDomainModel
import com.todotrek.feature.todo.util.ToDoListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    geToDoListUseCase: GetToDoListUseCase,
    private val addToDoUseCase: AddToDoUseCase
) : ViewModel() {
    init {

    }

    val toDoListUiState: StateFlow<ToDoListUiState> = geToDoListUseCase.invoke()
        .map { result ->
            result.fold(
                onSuccess = { currenciesList ->
                    if (currenciesList.isEmpty()) {
                        ToDoListUiState.Error(Exception())
                    } else {
                        currenciesList.map {
                            it.toPresentationModel()
                        }.run {
                            ToDoListUiState.Success(this)
                        }
                    }
                },
                onFailure = {
                    ToDoListUiState.Error(it)
                }
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ToDoListUiState.Loading
        )

    fun addNewToDo(addToDoModel: AddToDoModel) {
        viewModelScope.launch {
            addToDoUseCase.invoke(addToDoModel.toDomainModel())
        }
    }
}