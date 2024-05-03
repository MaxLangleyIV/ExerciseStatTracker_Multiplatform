package com.langley.exercisestattracker.features.library.schedules

import com.langley.exercisestattracker.core.domain.ExerciseRoutine

sealed interface ScheduleBuilderEvent {
    data class OnSearchStringChanged(val value: String) : ScheduleBuilderEvent
    data class AddToListOfRoutines(val routines: List<ExerciseRoutine>) : ScheduleBuilderEvent

    data object CloseExerciseSelector : ScheduleBuilderEvent
}