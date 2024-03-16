package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord

sealed interface WorkoutEvent {
    data object OpenExerciseSelector: WorkoutEvent
    data object CloseExerciseSelector: WorkoutEvent
    data class AddRecord(val record: ExerciseRecord): WorkoutEvent
    data class RemoveRecord(val recordName: String, val index: Int): WorkoutEvent
    data object SaveWorkout: WorkoutEvent
    data class MarkCompleted(val record: ExerciseRecord): WorkoutEvent
    data class RemoveFromCompleted(val index: Int): WorkoutEvent
    data class DefinitionSelected(val exerciseDefinition: ExerciseDefinition) : WorkoutEvent

    data object NextExercise
}