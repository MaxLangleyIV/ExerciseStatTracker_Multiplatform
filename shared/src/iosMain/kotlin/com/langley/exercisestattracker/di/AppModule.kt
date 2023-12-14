package com.langley.exercisestattracker.di

import com.langley.exercisestattracker.core.data.DatabaseDriverFactory
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase
import com.langley.exercisestattracker.exerciseLibrary.data.SqlDelightExerciseDefDataSource
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSource

actual class AppModule {
    actual val exerciseDefinitionDataSource: ExerciseDefinitionDataSource by lazy {
        SqlDelightExerciseDefDataSource(
            database = ExerciseStatTrackerDatabase(
                driver = DatabaseDriverFactory().createDriver()
            )
        )
    }
}