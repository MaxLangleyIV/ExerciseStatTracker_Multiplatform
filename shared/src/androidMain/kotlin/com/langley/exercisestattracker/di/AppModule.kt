package com.langley.exercisestattracker.di

import android.content.Context
import com.langley.exercisestattracker.core.data.DatabaseDriverFactory
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase
import com.langley.exercisestattracker.core.data.SqlDelightExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource

actual class AppModule(
    private val context: Context
) {
    actual val exerciseAppDataSource: ExerciseAppDataSource by lazy {
        SqlDelightExerciseAppDataSource(
            database = ExerciseStatTrackerDatabase(
                driver = DatabaseDriverFactory(context).createDriver()
            )
        )
    }
}