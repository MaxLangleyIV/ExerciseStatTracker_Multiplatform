package com.langley.exercisestattracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.langley.exercisestattracker.core.data.DatabaseDriverFactory
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase
import com.langley.exercisestattracker.core.data.SqlDelightExerciseAppDataSource
import com.langley.exercisestattracker.core.data.createDataStorePreferences
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
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

    actual val preferencesDataStore: DataStore<Preferences> by lazy {
        createDataStorePreferences(
            context = context
        )
    }
}