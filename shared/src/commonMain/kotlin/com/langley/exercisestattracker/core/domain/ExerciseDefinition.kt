package com.langley.exercisestattracker.core.domain

data class ExerciseDefinition(
    val exerciseDefinitionId: Long?,
    val exerciseName: String,
    val bodyRegion: String,
    val targetMuscles: String,
    val description: String,
    val isWeighted: Long,
    val isCardio: Long,
    val isCalisthenic: Long,
    val isTimed: Long,
    val hasDistance: Long,
    val isFavorite: Long,
    val dateCreated: Long?
)
