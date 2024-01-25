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
        val definitionId = definition.exerciseDefinitionId

        if (definitionId != null){

            val index = definitionId.toInt()

            dummyDataList.removeAt(index)
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
        val index = exerciseDefinitionId.toInt()

        dummyDataList.removeAt(index)
    }
}