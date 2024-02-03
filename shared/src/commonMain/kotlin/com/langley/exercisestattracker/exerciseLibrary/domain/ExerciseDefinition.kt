package com.langley.exercisestattracker.exerciseLibrary.domain

data class ExerciseDefinition(
    val exerciseDefinitionId: Long?,
    val exerciseName: String,
    val bodyRegion: String,
    val targetMuscles: String,
    val description: String,
    val isFavorite: Long,
    val dateCreated: Long?
)
