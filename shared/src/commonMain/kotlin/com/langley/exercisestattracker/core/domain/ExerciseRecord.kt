package com.langley.exercisestattracker.core.domain

import com.langley.exercisestattracker.library.ExerciseLibraryFilterType

data class ExerciseRecord(
    val exerciseRecordId: Long?,
    val dateCompleted: Long,
    val exerciseName: String,
    val weightUsed: Float,
    val isCardio: Boolean,
    val isCalisthenic: Boolean,
    val duration: Float,
    val distance: Float,
    val repsCompleted: Int,
    val rpe: Int,
    val notes: String,
    val userId: Long,
    val currentBodyWeight: Int
)
