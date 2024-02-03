package com.langley.exercisestattracker.exerciseLibrary.domain

data class ExerciseSchedule(
    val exerciseScheduleId: Long?,
    val exerciseScheduleName: String,
    val exerciseRoutineCSV: String,
    val isFavorite: Long,
    val dateCreated:Long?
)