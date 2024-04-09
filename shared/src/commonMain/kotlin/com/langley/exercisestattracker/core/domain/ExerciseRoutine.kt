package com.langley.exercisestattracker.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseRoutine(
    val exerciseRoutineId: Long? = null,
    val routineName: String = "",
    val exerciseCSV: String = "", // CSV of exercise IDs
    val repsCSV: String = "",
    val description: String = "",
    val isFavorite: Boolean = false,
    val dateCreated: Long? = null
)