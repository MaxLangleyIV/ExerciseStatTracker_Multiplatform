package com.langley.exercisestattracker.core.domain

data class ExerciseRoutine(
    val exerciseRoutineId: Long?,
    val routineName: String,
    val exerciseCSV: String,
    val repsCSV: String,
    val description: String,
    val isFavorite: Long,
    val dateCreated: Long?
)