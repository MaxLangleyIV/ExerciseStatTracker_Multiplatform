package com.langley.exercisestattracker.exerciseLibrary.data

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionModel
import database.ExerciseDefinition

fun ExerciseDefinition.toExerciseDefinitionModel(): ExerciseDefinitionModel
{
    return ExerciseDefinitionModel(
        exerciseDefinitionId,
        exerciseName,
        bodyRegion,
        targetMuscles,
        description,
        isFavorite,
        dateCreated
    )
}