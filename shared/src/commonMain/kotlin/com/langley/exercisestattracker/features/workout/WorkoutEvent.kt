package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseDefinition

sealed interface WorkoutEvent {
    data object AddNewExercise: WorkoutEvent
    data class AddSetOf(val exercise: ExerciseDefinition): WorkoutEvent
    data object RemoveExercise: WorkoutEvent
    data object MarkCompleted
    data object NextExercise
}