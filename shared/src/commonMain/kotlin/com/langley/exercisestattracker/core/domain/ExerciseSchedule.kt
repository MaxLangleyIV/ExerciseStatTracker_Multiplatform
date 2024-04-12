package com.langley.exercisestattracker.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSchedule(
    val exerciseScheduleId: Long? = null,
    val exerciseScheduleName: String = "",
    val description: String = "",
    val exerciseRoutineCSV: String = "",
    val isFavorite: Boolean = false,
    val dateCreated:Long? = null
)