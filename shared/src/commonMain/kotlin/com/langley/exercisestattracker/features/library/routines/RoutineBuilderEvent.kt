package com.langley.exercisestattracker.features.library.routines

import com.langley.exercisestattracker.core.domain.ExerciseDefinition

sealed interface RoutineBuilderEvent {
    data class OnSearchStringChanged(val value: String) : RoutineBuilderEvent
    data class AddToListOfExercises(val exercises: List<ExerciseDefinition>) : RoutineBuilderEvent
    data object OpenExerciseSelector : RoutineBuilderEvent
    data object CloseExerciseSelector : RoutineBuilderEvent
}