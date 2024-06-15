package com.todotrek.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todotrek.feature.todo.mapper.toPresentationModel
import com.todotrek.feature.todo.model.AddToDoModel
import com.todotrek.domain.usecases.AddToDoUseCase
import com.todotrek.domain.usecases.GetToDoListUseCase
import com.todotrek.feature.todo.mapper.toDomainModel
import com.todotrek.feature.todo.util.AddToDoAction
import com.todotrek.feature.todo.util.AddToDoUiState
import com.todotrek.feature.todo.util.ToDoListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    geToDoListUseCase: GetToDoListUseCase,
    private val addToDoUseCase: AddToDoUseCase
) : ViewModel() {
    val actionFlow = MutableSharedFlow<AddToDoAction>()
    private val addToDoUiStateMutableFlow: MutableStateFlow<AddToDoUiState> =
        MutableStateFlow(AddToDoUiState.None)
    val addToDoUiStateFlow: MutableStateFlow<AddToDoUiState>
        get() = addToDoUiStateMutableFlow

    init {
        viewModelScope.launch {
            handleActions()
        }
    }

    private suspend fun handleActions() {
        actionFlow.collect { action ->
            when (action) {
                is AddToDoAction.Add -> {
                    addToDoUiStateMutableFlow.value = AddToDoUiState.Loading

                    if (action.addToDoModel.title.trim().lowercase() == "error") {
                        delay(3000)
                        addToDoUiStateMutableFlow.value = AddToDoUiState.Error
                    } else {
                        delay(3000)
                        addNewToDo(action.addToDoModel)
                    }
                }

                AddToDoAction.AddError -> {
                    addToDoUiStateMutableFlow.value = AddToDoUiState.Error
                }

                AddToDoAction.AddSuccess -> {
                    addToDoUiStateMutableFlow.value = AddToDoUiState.Success
                }

                AddToDoAction.None -> {
                    addToDoUiStateMutableFlow.value = AddToDoUiState.None
                }
            }
        }
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

    private fun addNewToDo(addToDoModel: AddToDoModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addToDoUseCase.invoke(addToDoModel.toDomainModel()).fold(
                    onSuccess = {
                        actionFlow.emit(AddToDoAction.AddSuccess)
                    },
                    onFailure = {
                        actionFlow.emit(AddToDoAction.AddError)
                    }
                )
            }
        }
    }
}