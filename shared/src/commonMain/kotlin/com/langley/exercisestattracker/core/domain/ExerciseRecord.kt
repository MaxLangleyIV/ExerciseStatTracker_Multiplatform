package com.langley.exercisestattracker.core.domain

data class ExerciseRecord(
    val exerciseRecordId: Long?,
    val dateCompleted: Long,
    val exerciseName: String,
    val weightUsed: Double,
    val repsCompleted: Int,
    val rpe: Int,
    val description: String,
    val notes: String,
    val userId: Long
)
