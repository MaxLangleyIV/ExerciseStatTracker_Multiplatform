package com.langley.exercisestattracker.exerciseLibrary.domain

data class ExerciseDefinition(
    val exerciseDefinitionId: Int?,
    val exerciseName: String,
    val bodyRegion: String,
    val targetMuscles: String,
    val description: String,
    val isFavorite: Int?,
    val dateCreated: Int?
)
