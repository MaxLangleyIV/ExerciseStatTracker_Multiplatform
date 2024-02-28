package com.langley.exercisestattracker.core.domain

data class ExerciseRoutine(
    val exerciseRoutineId: Long? = null,
    val routineName: String = "",
    val exerciseCSV: String = "",
    val repsCSV: String = "",
    val description: String = "",
    val isFavorite: Boolean = false,
    val dateCreated: Long? = null
)