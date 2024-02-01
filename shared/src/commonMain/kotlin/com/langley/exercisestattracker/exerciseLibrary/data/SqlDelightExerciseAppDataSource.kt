package com.langley.exercisestattracker.exerciseLibrary.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseAppDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightExerciseAppDataSource(
    database: ExerciseStatTrackerDatabase
): ExerciseAppDataSource {

    private val exerciseDefinitionQueries = database.exerciseDefinitionQueries
    private val exerciseRoutineQueries = database.exerciseRoutineQueries
    private val exerciseScheduleQueries = database.exerciseScheduleQueries


    override fun getDefinitions(): Flow<List<ExerciseDefinition>> {
        return exerciseDefinitionQueries
            .getExerciseDefinitions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { dbExerciseDefinitions ->
                dbExerciseDefinitions.map { dbExerciseDefinition ->
                    dbExerciseDefinition.toExerciseDefinition()
                }
            }
    }

    override suspend fun insertOrReplaceDefinition(definition: ExerciseDefinition) {
        exerciseDefinitionQueries.insertOrReplaceExerciseDefinition(
            definition.exerciseDefinitionId,
            definition.exerciseName,
            definition.bodyRegion,
            definition.targetMuscles,
            definition.description,
            0,
            Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteDefinition(exerciseDefinitionId: Long) {
        exerciseDefinitionQueries.deleteExerciseDefinition(exerciseDefinitionId)
    }

    override fun getRoutines(): Flow<List<ExerciseDefinition>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplaceRoutine(definition: ExerciseDefinition) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRoutine(exerciseDefinitionId: Long) {
        TODO("Not yet implemented")
    }

    override fun getSchedules(): Flow<List<ExerciseDefinition>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplaceSchedule(definition: ExerciseDefinition) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSchedule(exerciseDefinitionId: Long) {
        TODO("Not yet implemented")
    }

    override fun getRecords(): Flow<List<ExerciseDefinition>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplaceRecord(definition: ExerciseDefinition) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecord(exerciseDefinitionId: Long) {
        TODO("Not yet implemented")
    }
}