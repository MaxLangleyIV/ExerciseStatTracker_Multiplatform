package com.langley.exercisestattracker.exerciseLibrary.domain

import kotlinx.coroutines.flow.Flow

interface ExerciseDefinitionDataSource {
    fun getDefinitions(): Flow<List<ExerciseDefinition>>
    fun getFavoriteDefinitions(): Flow<List<ExerciseDefinition>>
    suspend fun insertOrReplaceDefinition(definition: ExerciseDefinition)
    suspend fun deleteDefinition(exerciseDefinitionId: Int)

}