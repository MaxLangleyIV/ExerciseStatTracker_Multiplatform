package com.langley.exercisestattracker.core.domain

data class ExerciseRecord(
    val exerciseRecordId: Long? = null,
    val dateCompleted: Long = 0,
    val exerciseName: String = "",
    val weightUsed: Float = 0F,
    val weightIsPounds: Boolean = true,
    val weightIsKilos: Boolean = false,
    val isCardio: Boolean = false,
    val isCalisthenic: Boolean = false,
    val duration: Float = 0F,
    val distance: Float = 0F,
    val repsCompleted: Int = 0,
    val rpe: Int = 0,
    val notes: String = "",
    val userId: Long = 0,
    val currentBodyWeight: Int = 0
)
