package com.langley.exercisestattracker.exerciseLibrary.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionModel
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSourceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightExerciseDefDataSource(
    database: ExerciseStatTrackerDatabase
): ExerciseDefinitionDataSourceModel {

    private val exerciseDefinitionQueries = database.exerciseDefinitionQueries
    override fun getDefinitions(): Flow<List<ExerciseDefinitionModel>> {
        return exerciseDefinitionQueries
            .getExerciseDefinitions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { dbExerciseDefinitions ->
                dbExerciseDefinitions.map { dbExerciseDefinition ->
                    dbExerciseDefinition.toExerciseDefinitionModel()
                }
            }
    }

    override fun getFavoriteDefinitions(): Flow<List<ExerciseDefinitionModel>> {
        return exerciseDefinitionQueries
            .getFavoriteDefinitions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { dbExerciseDefinitions ->
                dbExerciseDefinitions.map { dbExerciseDefinition ->
                    dbExerciseDefinition.toExerciseDefinitionModel()
                }
            }
    }

    override suspend fun insertOrReplaceExerciseDefinition(definition: ExerciseDefinitionModel) {
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
}