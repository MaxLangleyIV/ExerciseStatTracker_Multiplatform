package com.langley.exercisestattracker.features.library.routines

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine

sealed interface RoutineBuilderEvent {
    data class OnSearchStringChanged(val value: String) : RoutineBuilderEvent
    data class AddToListOfExercises(val exercises: List<ExerciseDefinition>) : RoutineBuilderEvent
    data class RemoveExercise(val index: Int) : RoutineBuilderEvent
    data object OpenSelector : RoutineBuilderEvent
    data object CloseSelector : RoutineBuilderEvent
    data class InsertOrReplaceRoutine(val routine: ExerciseRoutine) : RoutineBuilderEvent
}