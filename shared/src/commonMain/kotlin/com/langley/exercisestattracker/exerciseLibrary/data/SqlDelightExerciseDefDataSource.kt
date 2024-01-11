package com.langley.exercisestattracker.exerciseLibrary.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightExerciseDefDataSource(
    database: ExerciseStatTrackerDatabase
): ExerciseDefinitionDataSource {

    private val exerciseDefinitionQueries = database.exerciseDefinitionQueries
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

    override fun getDefinitionsLikeName(searchString: String): Flow<List<ExerciseDefinition>> {
        return exerciseDefinitionQueries.getDefinitionsLikeName(searchString)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { dbExerciseDefinitions ->
                dbExerciseDefinitions.map { dbExerciseDefinition ->
                    dbExerciseDefinition.toExerciseDefinition()
                }
            }
    }

    override fun getFavoriteDefinitions(): Flow<List<ExerciseDefinition>> {
        return exerciseDefinitionQueries
            .getFavoriteDefinitions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { dbExerciseDefinitions ->
                dbExerciseDefinitions.map { dbExerciseDefinition ->
                    dbExerciseDefinition.toExerciseDefinition()
                }
            }
    }

    override suspend fun insertOrReplaceExerciseDefinition(definition: ExerciseDefinition) {
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