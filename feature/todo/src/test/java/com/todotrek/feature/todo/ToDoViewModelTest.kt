package com.todotrek.feature.todo

import app.cash.turbine.test
import com.todotrek.domain.model.ToDoItemDomainModel
import com.todotrek.domain.usecases.AddToDoUseCase
import com.todotrek.domain.usecases.GetToDoListUseCase
import com.todotrek.feature.todo.helper.MainDispatcherRule
import com.todotrek.feature.todo.mapper.toDomainModel
import com.todotrek.feature.todo.util.ToDoListUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ToDoViewModelTest {
    @get:Rule
    val rule = MainDispatcherRule()

    @MockK
    lateinit var getToDoListUseCase: GetToDoListUseCase

    @MockK
    lateinit var addToDoUseCase: AddToDoUseCase

    private lateinit var viewModel: ToDoViewModel
    private var expectedResponse: Result<List<ToDoItemDomainModel>> = Result.success(
        listOf(
            ToDoItemDomainModel(
                title = "test",
                description = "description",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ToDoViewModel(getToDoListUseCase, addToDoUseCase)
    }

    @Test
    fun getToDoListUiState() {
        coEvery { getToDoListUseCase.invoke() }.returns(flowOf(expectedResponse))

        runTest {
            viewModel.searchData("")

            viewModel.toDoListUiState.test {
                this.awaitItem().run {
                    assertEquals(expectedResponse,
                        (this as? ToDoListUiState.Success)?.data?.map {
                            it.toDomainModel()
                        })
                }
            }
        }
    }
}