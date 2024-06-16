package com.todotrek.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.todotrek.database.helper.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ToDoTrekDaoTest {
    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var database: ToDoTrekDatabase
    private lateinit var dao: ToDoTrekDao

    @Before
    fun setupDatabase() {
        //MockitoAnnotations.openMocks(this)

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoTrekDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.toDoTrekDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insert_ToDo_returns_success() {
        runTest {
            val item = ToDoEntity(
                title = "Test 1",
                description = "Description",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
            )

            dao.addToDo(item)

            dao.getToDoList().test {
                val list = awaitItem()
                assert(list.contains(item))
                cancel()
            }
        }
    }
}