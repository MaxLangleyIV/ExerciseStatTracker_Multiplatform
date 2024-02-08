package com.langley.exercisestattracker.di

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource

expect class AppModule {
    val exerciseAppDataSource: ExerciseAppDataSource
}