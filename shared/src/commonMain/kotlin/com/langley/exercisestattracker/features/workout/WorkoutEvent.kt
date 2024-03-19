package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord

sealed interface WorkoutEvent {
    data object OpenExerciseSelector: WorkoutEvent
    data object CloseExerciseSelector: WorkoutEvent
    data class AddRecordToMap(val record: ExerciseRecord): WorkoutEvent
    data class RemoveRecordFromMap(val recordName: String, val index: Int): WorkoutEvent
    data object SaveWorkout: WorkoutEvent
    data class MarkCompleted(val record: ExerciseRecord): WorkoutEvent
    data class RemoveFromCompleted(val index: Int): WorkoutEvent
    data class DefinitionSelected(val exerciseDefinition: ExerciseDefinition) : WorkoutEvent
    data class AddToListOfExercises(val exercises: List<ExerciseDefinition>) : WorkoutEvent
    data class RemoveFromListOfExercises(val exercise: ExerciseDefinition) : WorkoutEvent
    data class AddToListOfRecords(val exercises: List<ExerciseRecord>) : WorkoutEvent
    data class RemoveFromListOfRecords(val exercise: ExerciseRecord) : WorkoutEvent


    data object NextExercise
}