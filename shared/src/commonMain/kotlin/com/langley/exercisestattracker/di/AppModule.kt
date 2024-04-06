package com.langley.exercisestattracker.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AppModule {
    val exerciseAppDataSource: ExerciseAppDataSource

    val preferencesDataStore: DataStore<Preferences>
}