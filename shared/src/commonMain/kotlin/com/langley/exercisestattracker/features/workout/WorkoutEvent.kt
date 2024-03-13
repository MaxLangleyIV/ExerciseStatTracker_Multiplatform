package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseRecord

sealed interface WorkoutEvent {
    data object OpenExerciseSelector: WorkoutEvent
    data object CloseExerciseSelector: WorkoutEvent
    data class AddRecord(val record: ExerciseRecord): WorkoutEvent
    data class RemoveRecord(val record: ExerciseRecord): WorkoutEvent
    data object MarkCompleted
    data object NextExercise
}