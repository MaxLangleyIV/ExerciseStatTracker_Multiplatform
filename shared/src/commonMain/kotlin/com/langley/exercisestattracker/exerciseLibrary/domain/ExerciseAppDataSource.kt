package com.langley.exercisestattracker.exerciseLibrary.domain

import kotlinx.coroutines.flow.Flow

interface ExerciseAppDataSource {
    fun getDefinitions(): Flow<List<ExerciseDefinition>>
    suspend fun insertOrReplaceExerciseDefinition(definition: ExerciseDefinition)
    suspend fun deleteDefinition(exerciseDefinitionId: Long)


}