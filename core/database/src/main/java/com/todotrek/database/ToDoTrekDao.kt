package com.todotrek.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoTrekDao {
    @Query("SELECT * FROM todoentity")
    fun getToDoList(): Flow<List<ToDoEntity>>

    @Query("DELETE FROM todoentity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDo(item: ToDoEntity)
}