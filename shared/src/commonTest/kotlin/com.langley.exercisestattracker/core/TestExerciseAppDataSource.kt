package com.langley.exercisestattracker.core

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestExerciseAppDataSource(

    dummyDefinitions: List<ExerciseDefinition> = listOf(),
    dummyRoutines: List<ExerciseRoutine> = listOf(),
    dummySchedules: List<ExerciseSchedule> = listOf(),
    dummyRecords: List<ExerciseRecord> = listOf()

): ExerciseAppDataSource {

    private val dummyDefinitionsList = dummyDefinitions.toMutableList()
    private val dummyRoutinesList = dummyRoutines.toMutableList()
    private val dummySchedulesList = dummySchedules.toMutableList()
    private val dummyRecordsList = dummyRecords.toMutableList()



    override fun getDefinitions(): Flow<List<ExerciseDefinition>> {
        return flow {
            emit(dummyDefinitionsList)
        }
    }

    override suspend fun insertOrReplaceDefinition(definition: ExerciseDefinition) {

        val newDefinition: ExerciseDefinition?
        val definitionId = definition.exerciseDefinitionId

        if (definitionId != null){

            val index = definitionId.toInt()

            dummyDefinitionsList.removeAt(index)
            dummyDefinitionsList.add(definition)
        }
        else{

            newDefinition = definition.copy(
                exerciseDefinitionId = dummyDefinitionsList.size.toLong()
            )

            dummyDefinitionsList.add(newDefinition)
        }
    }

    override suspend fun deleteDefinition(exerciseDefinitionId: Long) {

        val index = exerciseDefinitionId.toInt()

        dummyDefinitionsList.removeAt(index)
    }

    override fun getRoutines(): Flow<List<ExerciseRoutine>> {
        return flow {
            emit(dummyRoutinesList)
        }
    }

    override suspend fun insertOrReplaceRoutine(routine: ExerciseRoutine) {
        val newRoutine: ExerciseRoutine?
        val routineId = routine.exerciseRoutineId

        if (routineId != null){

            val index = routineId.toInt()

            dummyRoutinesList.removeAt(index)
            dummyRoutinesList.add(routine)
        }
        else{

            newRoutine = routine.copy(
                exerciseRoutineId = dummyRoutinesList.size.toLong()
            )

            dummyRoutinesList.add(newRoutine)
        }
    }

    override suspend fun deleteRoutine(exerciseRoutineId: Long) {
        val index = exerciseRoutineId.toInt()

        dummyRoutinesList.removeAt(index)
    }

    override fun getSchedules(): Flow<List<ExerciseSchedule>> {
        return flow {
            emit(dummySchedulesList)
        }
    }

    override suspend fun insertOrReplaceSchedule(schedule: ExerciseSchedule) {
        val newSchedule: ExerciseSchedule?
        val scheduleId = schedule.exerciseScheduleId

        if (scheduleId != null){

            val index = scheduleId.toInt()

            dummySchedulesList.removeAt(index)
            dummySchedulesList.add(schedule)
        }
        else{

            newSchedule = schedule.copy(
                exerciseScheduleId = dummySchedulesList.size.toLong()
            )

            dummySchedulesList.add(newSchedule)
        }
    }

    override suspend fun deleteSchedule(exerciseScheduleId: Long) {
        val index = exerciseScheduleId.toInt()

        dummySchedulesList.removeAt(index)
    }

    override fun getRecords(): Flow<List<ExerciseRecord>> {
        return flow {
            emit(dummyRecordsList)
        }
    }

    override suspend fun insertOrReplaceRecord(record: ExerciseRecord) {
        val newRecord: ExerciseRecord?
        val recordId = record.exerciseRecordId

        if (recordId != null){

            val index = recordId.toInt()

            dummyRecordsList.removeAt(index)
            dummyRecordsList.add(record)
        }
        else{

            newRecord = record.copy(
                exerciseRecordId = dummyRecordsList.size.toLong()
            )

            dummyRecordsList.add(newRecord)
        }
    }

    override suspend fun deleteRecord(exerciseRecordId: Long) {
        val index = exerciseRecordId.toInt()

        dummyRecordsList.removeAt(index)
    }
}