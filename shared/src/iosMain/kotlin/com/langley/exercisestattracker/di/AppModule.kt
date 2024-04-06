package com.langley.exercisestattracker.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.langley.exercisestattracker.core.data.DatabaseDriverFactory
import com.langley.exercisestattracker.core.data.SqlDelightExerciseAppDataSource
import com.langley.exercisestattracker.core.data.getDataStore
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase

actual class AppModule {
    actual val exerciseAppDataSource: ExerciseAppDataSource by lazy {
        SqlDelightExerciseAppDataSource(
            database = ExerciseStatTrackerDatabase(
                driver = DatabaseDriverFactory().createDriver()
            )
        )
    }

    actual val preferencesDataStore: DataStore<Preferences> by lazy {
        getDataStore()
    }
}