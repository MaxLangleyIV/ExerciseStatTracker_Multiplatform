package com.langley.exercisestattracker.core.domain

data class ExerciseDefinition(
    val exerciseDefinitionId: Long? = null,
    val exerciseName: String = "",
    val bodyRegion: String = "",
    val targetMuscles: String = "",
    val description: String = "",
    val isWeighted: Boolean = false,
    val hasReps: Boolean = false,
    val isCardio: Boolean = false,
    val isCalisthenic: Boolean = false,
    val isTimed: Boolean = false,
    val defaultDuration: Long = 0,
    val hasDistance: Boolean = false,
    val defaultDistance: Long = 0,
    val isFavorite: Boolean = false,
    val dateCreated: Long? = null
)
