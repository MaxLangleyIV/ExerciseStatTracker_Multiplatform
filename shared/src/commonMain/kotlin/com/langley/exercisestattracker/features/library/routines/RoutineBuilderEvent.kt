package com.langley.exercisestattracker.features.library.routines

sealed interface RoutineBuilderEvent {
    data class OnSearchStringChanged(val value: String) : RoutineBuilderEvent

    data object OpenExerciseSelector : RoutineBuilderEvent
    data object CloseExerciseSelector : RoutineBuilderEvent
}