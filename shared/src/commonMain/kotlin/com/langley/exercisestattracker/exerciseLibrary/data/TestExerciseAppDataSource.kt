package com.langley.exercisestattracker.exerciseLibrary.data

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
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