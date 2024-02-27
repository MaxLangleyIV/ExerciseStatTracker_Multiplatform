package com.langley.exercisestattracker.core.domain

data class ExerciseRecord(
    val exerciseRecordId: Long? = null,
    val dateCompleted: Long = 0,
    val exerciseName: String = "",
    val weightUsed: Float = 0F,
    val weightUnit: String = "lbs",
//    val weightIsKilos: Boolean = false,
    val isCardio: Boolean = false,
    val isCalisthenic: Boolean = false,
    val duration: String = "0",
    val distance: Float = 0F,
    val distanceUnit: String = "m",
    val repsCompleted: Int = 0,
    val rir: Int = 2,
    val notes: String = "",
    val userId: Long = 0,
    val currentBodyWeight: Int = 0
)
