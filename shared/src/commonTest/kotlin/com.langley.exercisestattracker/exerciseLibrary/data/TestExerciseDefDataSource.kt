package com.langley.exercisestattracker.exerciseLibrary.data

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestExerciseDefDataSource: ExerciseDefinitionDataSource {

    private val dummyData = ExerciseDefinitionDummyData()
    private val dummyDataList = dummyData.convertDummyDataToExerciseDef(dummyData.dummyDefinitionData)

    private val exerciseDefinitions = flow<List<ExerciseDefinition>> {
        dummyDataList
    }

    override fun getDefinitions(): Flow<List<ExerciseDefinition>> {
        return exerciseDefinitions
    }

    override suspend fun insertOrReplaceExerciseDefinition(definition: ExerciseDefinition) {

        for (def in dummyDataList){

            if (def.exerciseDefinitionId == definition.exerciseDefinitionId){
                dummyDataList.remove(def)
            }
        }

        dummyDataList.add(definition)
    }

    override suspend fun deleteDefinition(exerciseDefinitionId: Long) {
        
        for (def in dummyDataList){

            if (def.exerciseDefinitionId == exerciseDefinitionId){
                dummyDataList.remove(def)
            }
        }
    }
}