package com.langley.exercisestattracker.exerciseLibrary.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestExerciseDefDataSource: ExerciseDefinitionDataSource {

    private val dummyDataList = mutableListOf<ExerciseDefinition>()


    override fun getDefinitions(): Flow<List<ExerciseDefinition>> {
        return flow {
            emit(dummyDataList)
        }
    }

    override suspend fun insertOrReplaceExerciseDefinition(definition: ExerciseDefinition) {

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
}