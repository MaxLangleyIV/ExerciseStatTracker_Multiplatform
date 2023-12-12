package com.langley.exercisestattracker.exerciseLibrary.domain

import kotlinx.coroutines.flow.Flow

interface ExerciseDefinitionDataSourceModel {
    fun getDefinitions(): Flow<List<ExerciseDefinitionModel>>
    fun getFavoriteDefinitions(): Flow<List<ExerciseDefinitionModel>>
    suspend fun insertOrReplaceExerciseDefinition(definition: ExerciseDefinitionModel)
    suspend fun deleteDefinition(exerciseDefinitionId: Int)

}