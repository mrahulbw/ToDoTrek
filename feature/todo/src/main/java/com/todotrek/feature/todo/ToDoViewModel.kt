package com.todotrek.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todotrek.feature.todo.mapper.toPresentationModel
import com.todotrek.feature.todo.model.AddToDoModel
import com.todotrek.domain.usecases.AddToDoUseCase
import com.todotrek.domain.usecases.GetToDoListUseCase
import com.todotrek.feature.todo.mapper.toDomainModel
import com.todotrek.feature.todo.model.ToDoModel
import com.todotrek.feature.todo.util.AddToDoAction
import com.todotrek.feature.todo.util.AddToDoUiState
import com.todotrek.feature.todo.util.ToDoListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
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

    private val searchTriggerFlow = MutableSharedFlow<String>(replay = 1)

    init {
        viewModelScope.launch {
            searchData("")
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

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val toDoListUiState: StateFlow<ToDoListUiState<ToDoModel>> = searchTriggerFlow
        .debounce(500)
        .flatMapLatest { query ->
            geToDoListUseCase.invoke()
                .map { result ->
                    result.fold(
                        onSuccess = { toDoList ->
                            if (toDoList.isEmpty()) {
                                ToDoListUiState.Error(Exception())
                            } else {
                                toDoList.map {
                                    it.toPresentationModel()
                                }.run {
                                    ToDoListUiState.Success(this.filter {
                                        it.title.contains(query.lowercase())
                                    })
                                }
                            }
                        },
                        onFailure = {
                            ToDoListUiState.Error(it)
                        }
                    )
                }
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

    suspend fun searchData(query: String) {
        searchTriggerFlow.emit(query)
    }
}