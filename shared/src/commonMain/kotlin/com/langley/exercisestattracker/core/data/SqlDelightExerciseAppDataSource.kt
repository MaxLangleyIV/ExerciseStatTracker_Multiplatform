package com.langley.exercisestattracker.core.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
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
    private val exerciseRecordQueries = database.exerciseRecordQueries


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
            definition.isWeighted,
            definition.isCardio,
            definition.isCalisthenic,
            definition.isTimed,
            definition.hasDistance,
            definition.isFavorite,
            Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteDefinition(exerciseDefinitionId: Long) {
        exerciseDefinitionQueries.deleteExerciseDefinition(exerciseDefinitionId)
    }

    override fun getRoutines(): Flow<List<ExerciseRoutine>> {
        return exerciseRoutineQueries
            .getExerciseRoutines()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { dbExerciseRoutines ->
                dbExerciseRoutines.map { dbExerciseRoutine ->
                    dbExerciseRoutine.toExerciseRoutine()
                }
            }
    }

    override suspend fun insertOrReplaceRoutine(routine: ExerciseRoutine) {
        exerciseRoutineQueries.insertOrReplaceExerciseRoutine(
            exerciseRoutineId = routine.exerciseRoutineId,
            routineName = routine.routineName,
            exercisesCSV = routine.exerciseCSV,
            repsCSV = routine.repsCSV,
            description = routine.description,
            isFavorite = routine.isFavorite,
            dateCreated = Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteRoutine(exerciseRoutineId: Long) {
        exerciseRoutineQueries.deleteExerciseRoutine(exerciseRoutineId)
    }

    override fun getSchedules(): Flow<List<ExerciseSchedule>> {
        return exerciseScheduleQueries
            .getExerciseSchedules()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { dbExerciseSchedules ->
                dbExerciseSchedules.map { dbExerciseSchedule ->
                    dbExerciseSchedule.toExerciseSchedule()
                }
            }
    }

    override suspend fun insertOrReplaceSchedule(schedule: ExerciseSchedule) {
        exerciseScheduleQueries.insertOrReplaceExerciseSchedule(
            exerciseScheduleId = schedule.exerciseScheduleId,
            exerciseScheduleName = schedule.exerciseScheduleName,
            exerciseRoutineCSV = schedule.exerciseRoutineCSV,
            isFavorite = schedule.isFavorite,
            dateCreated = Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteSchedule(exerciseScheduleId: Long) {
        exerciseScheduleQueries.deleteExerciseSchedule(exerciseScheduleId)
    }

    override fun getRecords(): Flow<List<ExerciseRecord>> {
        return exerciseRecordQueries
            .getExerciseRecords()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { dbExerciseRecords ->
                dbExerciseRecords.map { dbExerciseRecord ->
                    dbExerciseRecord.toExerciseRecord()
                }
            }
    }

    override suspend fun insertOrReplaceRecord(record: ExerciseRecord) {
        exerciseRecordQueries.insertOrReplaceExerciseRecord(
            exerciseRecordId = record.exerciseRecordId,
            dateCompleted = record.dateCompleted,
            exerciseName = record.exerciseName,
            weightUsed = record.weightUsed.toDouble(),
            isCardio = if (record.isCardio){ 1 } else{ 0 },
            isCalisthenic = if (record.isCalisthenic){ 1 } else{ 0 },
            duration = record.duration.toDouble(),
            distance = record.distance.toDouble(),
            repsCompleted = record.repsCompleted.toLong(),
            rpe = record.rpe.toLong(),
            notes = record.notes,
            userId = record.userId,
            currentBodyWeight = record.currentBodyWeight.toLong()
        )
    }

    override suspend fun deleteRecord(exerciseRecordId: Long) {
        exerciseRecordQueries.deleteExerciseRecord(exerciseRecordId)
    }
}