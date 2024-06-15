package com.todotrek.data.repository

import com.todotrek.data.mapper.toDomainItemModel
import com.todotrek.data.mapper.toEntityModel
import com.todotrek.database.ToDoTrekDao
import com.todotrek.domain.model.ToDoItemDomainModel
import com.todotrek.domain.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoTrekDao: ToDoTrekDao
) : ToDoRepository {
    override fun getToDoList(): Flow<List<ToDoItemDomainModel>> {
        return toDoTrekDao.getToDoList().map { cachedToDOs ->
            cachedToDOs.map { it.toDomainItemModel() }
        }.flowOn(Dispatchers.IO).map {
            it
        }
    }

    override suspend fun addToDo(newToDo: ToDoItemDomainModel) =
        toDoTrekDao.addToDo(newToDo.toEntityModel())
}