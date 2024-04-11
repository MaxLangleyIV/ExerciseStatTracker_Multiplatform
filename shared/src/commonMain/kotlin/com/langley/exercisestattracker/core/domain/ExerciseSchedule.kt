package com.langley.exercisestattracker.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSchedule(
    val exerciseScheduleId: Long? = null,
    val exerciseScheduleName: String = "",
    val exerciseRoutineCSV: String = "",
    val isFavorite: Long = 0,
    val dateCreated:Long? = null
)