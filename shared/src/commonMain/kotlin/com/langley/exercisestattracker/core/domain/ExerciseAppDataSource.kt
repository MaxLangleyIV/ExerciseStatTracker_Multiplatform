package com.langley.exercisestattracker.core.domain

import kotlinx.coroutines.flow.Flow

interface ExerciseAppDataSource {

    // Definitions
    fun getDefinitions(): Flow<List<ExerciseDefinition>>
    suspend fun insertOrReplaceDefinition(definition: ExerciseDefinition)
    suspend fun deleteDefinition(exerciseDefinitionId: Long)

    //Routines
    fun getRoutines(): Flow<List<ExerciseRoutine>>
    suspend fun insertOrReplaceRoutine(routine: ExerciseRoutine)
    suspend fun deleteRoutine(exerciseRoutineId: Long)

    //Schedules
    fun getSchedules(): Flow<List<ExerciseSchedule>>
    suspend fun insertOrReplaceSchedule(schedule: ExerciseSchedule)
    suspend fun deleteSchedule(exerciseScheduleId: Long)

    //Records
    fun getRecords(): Flow<List<ExerciseRecord>>
    suspend fun insertOrReplaceRecord(record: ExerciseRecord)
    suspend fun deleteRecord(exerciseRecordId: Long)


}