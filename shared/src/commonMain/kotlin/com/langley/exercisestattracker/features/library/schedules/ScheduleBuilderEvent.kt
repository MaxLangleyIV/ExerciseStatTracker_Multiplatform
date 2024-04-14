package com.langley.exercisestattracker.features.library.schedules

sealed interface ScheduleBuilderEvent {
    data class OnSearchStringChanged(val value: String) : ScheduleBuilderEvent

    data object CloseExerciseSelector : ScheduleBuilderEvent
}