package com.langley.exercisestattracker.core.domain

data class ExerciseDefinition(
    val exerciseDefinitionId: Long?,
    val exerciseName: String,
    val bodyRegion: String,
    val targetMuscles: String,
    val description: String,
    val isWeighted: Boolean,
    val hasReps: Boolean,
    val isCardio: Boolean,
    val isCalisthenic: Boolean,
    val isTimed: Boolean,
    val defaultDuration: Long,
    val hasDistance: Boolean,
    val defaultDistance: Long,
    val isFavorite: Boolean,
    val dateCreated: Long?
)
