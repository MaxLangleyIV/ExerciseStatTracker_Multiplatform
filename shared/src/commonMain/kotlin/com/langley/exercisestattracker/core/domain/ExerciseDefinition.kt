package com.langley.exercisestattracker.core.domain

data class ExerciseDefinition(
    val exerciseDefinitionId: Long?,
    val exerciseName: String,
    val bodyRegion: String,
    val targetMuscles: String,
    val description: String,
    val isCalisthenic: Long,
    val isTimed: Long,
    val duration: Long,
    val isFavorite: Long,
    val dateCreated: Long?
)
