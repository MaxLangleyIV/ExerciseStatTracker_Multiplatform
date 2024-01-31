package com.langley.exercisestattracker.di

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseAppDataSource

expect class AppModule {
    val exerciseAppDataSource: ExerciseAppDataSource
}