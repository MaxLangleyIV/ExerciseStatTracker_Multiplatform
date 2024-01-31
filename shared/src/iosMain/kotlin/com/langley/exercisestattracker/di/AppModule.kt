package com.langley.exercisestattracker.di

import com.langley.exercisestattracker.core.data.DatabaseDriverFactory
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase
import com.langley.exercisestattracker.exerciseLibrary.data.SqlDelightExerciseAppDataSource
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseAppDataSource

actual class AppModule {
    actual val exerciseAppDataSource: ExerciseAppDataSource by lazy {
        SqlDelightExerciseAppDataSource(
            database = ExerciseStatTrackerDatabase(
                driver = DatabaseDriverFactory().createDriver()
            )
        )
    }
}