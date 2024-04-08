package com.langley.exercisestattracker.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSchedule(
    val exerciseScheduleId: Long?,
    val exerciseScheduleName: String,
    val exerciseRoutineCSV: String,
    val isFavorite: Long,
    val dateCreated:Long?
)