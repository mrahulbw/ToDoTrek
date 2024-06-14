package com.todotrek.database.di

import android.content.Context
import androidx.room.Room
import com.todotrek.database.ToDoTrekDao
import com.todotrek.database.ToDoTrekDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideToDoTrekDatabase(@ApplicationContext appContext: Context): ToDoTrekDatabase =
        Room.databaseBuilder(
            appContext,
            ToDoTrekDatabase::class.java,
            "ToDoTrekDatabase"
        ).build()

    @Provides
    fun provideToDoTrekDao(toDoTrekDatabase: ToDoTrekDatabase): ToDoTrekDao =
        toDoTrekDatabase.toDoTrekDao()
}