package com.todotrek.domain

import app.cash.turbine.test
import com.todotrek.domain.model.ToDoItemDomainModel
import com.todotrek.domain.repository.ToDoRepository
import com.todotrek.domain.usecases.GetToDoListUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetToDoListUseCaseTest {
    @MockK
    lateinit var toDoRepository: ToDoRepository

    private lateinit var getToDoListUseCase: GetToDoListUseCase
    private lateinit var expectedResponse: List<ToDoItemDomainModel>
    private lateinit var expectedErrorResponse: Throwable

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getToDoListUseCase = GetToDoListUseCase(toDoRepository)

        expectedResponse = listOf(
            ToDoItemDomainModel(
                title = "test",
                description = "description",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )

        expectedErrorResponse = Throwable()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getToDo_success() {
        coEvery { toDoRepository.getToDoList() }.returns(flowOf(expectedResponse))

        runTest {
            val response = getToDoListUseCase.invoke()

            response.test {
                this.awaitItem().fold(
                    onSuccess = {
                                assertEquals(expectedResponse, it)
                    },
                    onFailure = {
                        assertEquals(expectedErrorResponse, it)
                    }
                )

                awaitComplete()
            }
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}