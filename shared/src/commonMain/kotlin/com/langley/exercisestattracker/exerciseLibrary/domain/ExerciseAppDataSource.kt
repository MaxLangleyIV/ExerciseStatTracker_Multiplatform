package com.langley.exercisestattracker.exerciseLibrary.domain

import kotlinx.coroutines.flow.Flow

interface ExerciseAppDataSource {

    // Definitions
    fun getDefinitions(): Flow<List<ExerciseDefinition>>
    suspend fun insertOrReplaceDefinition(definition: ExerciseDefinition)
    suspend fun deleteDefinition(exerciseDefinitionId: Long)

    //Routines
    fun getRoutines(): Flow<List<ExerciseDefinition>>
    suspend fun insertOrReplaceRoutine(definition: ExerciseDefinition)
    suspend fun deleteRoutine(exerciseDefinitionId: Long)

    //Schedules
    fun getSchedules(): Flow<List<ExerciseDefinition>>
    suspend fun insertOrReplaceSchedule(definition: ExerciseDefinition)
    suspend fun deleteSchedule(exerciseDefinitionId: Long)

    //Records
    fun getRecords(): Flow<List<ExerciseDefinition>>
    suspend fun insertOrReplaceRecord(definition: ExerciseDefinition)
    suspend fun deleteRecord(exerciseDefinitionId: Long)


}