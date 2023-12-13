package com.langley.exercisestattracker.exerciseLibrary.data


fun database.ExerciseDefinition.toExerciseDefinition(): com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
{
    return com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition(
        exerciseDefinitionId,
        exerciseName,
        bodyRegion,
        targetMuscles,
        description,
        isFavorite,
        dateCreated
    )
}