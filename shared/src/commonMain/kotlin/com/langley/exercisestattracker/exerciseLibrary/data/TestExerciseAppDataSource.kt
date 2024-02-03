package com.langley.exercisestattracker.exerciseLibrary.data

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseRecord
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseRoutine
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseSchedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestExerciseAppDataSource: ExerciseAppDataSource {

    private val dummyDataList = mutableListOf<ExerciseDefinition>()


    override fun getDefinitions(): Flow<List<ExerciseDefinition>> {
        return flow {
            emit(dummyDataList)
        }
    }

    override suspend fun insertOrReplaceDefinition(definition: ExerciseDefinition) {

        var newDefinition: ExerciseDefinition? = null

        if (definition.exerciseDefinitionId != null){

            for (def in dummyDataList){

                if (def.exerciseDefinitionId == definition.exerciseDefinitionId){
                    dummyDataList.remove(def)
                }
            }

            dummyDataList.add(definition)
        }
        else{

            newDefinition = definition.copy(
                exerciseDefinitionId = dummyDataList.size.toLong()
            )

            dummyDataList.add(newDefinition)
        }
    }

    override suspend fun deleteDefinition(exerciseDefinitionId: Long) {
        
        for (def in dummyDataList){

            if (def.exerciseDefinitionId == exerciseDefinitionId){
                dummyDataList.remove(def)
            }
        }
    }

    override fun getRoutines(): Flow<List<ExerciseRoutine>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplaceRoutine(routine: ExerciseRoutine) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRoutine(exerciseRoutineId: Long) {
        TODO("Not yet implemented")
    }

    override fun getSchedules(): Flow<List<ExerciseSchedule>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplaceSchedule(schedule: ExerciseSchedule) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSchedule(exerciseScheduleId: Long) {
        TODO("Not yet implemented")
    }

    override fun getRecords(): Flow<List<ExerciseRecord>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplaceRecord(record: ExerciseRecord) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecord(exerciseRecordId: Long) {
        TODO("Not yet implemented")
    }
}