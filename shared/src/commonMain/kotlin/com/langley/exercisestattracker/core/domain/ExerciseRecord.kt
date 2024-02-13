package com.langley.exercisestattracker.core.domain

import com.langley.exercisestattracker.library.ExerciseLibraryFilterType

data class ExerciseRecord(
    val exerciseRecordId: Long?,
    val dateCompleted: Long,
    val exerciseName: String,
    val isCalisthenic: Long,
    val isTimed: Long,
    val duration: Long,
    val weightUsed: Double,
    val repsCompleted: Int,
    val rpe: Int,
    val description: String,
    val notes: String,
    val userId: Long,
    val currentBodyWeight: Long
)
