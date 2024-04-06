package com.langley.exercisestattracker.di

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AppModule {
    val exerciseAppDataSource: ExerciseAppDataSource
}