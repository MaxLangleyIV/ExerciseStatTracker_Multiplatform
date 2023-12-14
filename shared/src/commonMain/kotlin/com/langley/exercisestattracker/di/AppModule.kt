package com.langley.exercisestattracker.di

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSource

expect class AppModule {
    val exerciseDefinitionDataSource: ExerciseDefinitionDataSource
}