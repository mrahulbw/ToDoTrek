package com.todotrek.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ToDoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ToDoTrekDatabase : RoomDatabase() {
    abstract fun toDoTrekDao(): ToDoTrekDao
}
