package com.langley.exercisestattracker.exerciseLibrary.data

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestExerciseDefDataSource(dummyExerciseDefData: List<ExerciseDefinition>): ExerciseDefinitionDataSource {

    private val dummyDataList = dummyExerciseDefData.toMutableList()

    override fun getDefinitions(): Flow<List<ExerciseDefinition>> {
        return flow {
            emit(dummyDataList)
        }
    }

    override suspend fun insertOrReplaceExerciseDefinition(definition: ExerciseDefinition) {

        val newDefinition: ExerciseDefinition?

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
}