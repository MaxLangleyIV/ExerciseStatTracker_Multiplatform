package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType

sealed interface WorkoutEvent {

    // Main Events
    data object OpenExerciseSelector: WorkoutEvent
    data object CloseExerciseSelector: WorkoutEvent
    data object SaveWorkout: WorkoutEvent
    data class MarkCompleted(val index: Int, val record: ExerciseRecord): WorkoutEvent
    data class MarkIncomplete(val index: Int, val record: ExerciseRecord): WorkoutEvent
    data class RemoveFromListOfExercises(val exercise: ExerciseDefinition) : WorkoutEvent
    data class AddToListOfRecords(val records: List<ExerciseRecord>) : WorkoutEvent
    data class RemoveFromListOfRecords(val index: Int) : WorkoutEvent
    data class UpdateRecordInList(val index: Int, val newRecord: ExerciseRecord) : WorkoutEvent
    data class SelectSet(val record: ExerciseRecord): WorkoutEvent
    data object ClearSelectedSet: WorkoutEvent
    data class UpdateRepsFromString(val index: Int, val value: String) : WorkoutEvent
    data class UpdateWeightFromString(val index: Int, val value: String) : WorkoutEvent

    // Exercise Selector Events
    data class DefinitionSelected(val exerciseDefinition: ExerciseDefinition) : WorkoutEvent
    data class AddToListOfExercises(val exercises: List<ExerciseDefinition>) : WorkoutEvent
    data class OnSearchStringChanged(val string: String) : WorkoutEvent
    data class SetCurrentFilterType(val filter: ExerciseLibraryFilterType) : WorkoutEvent
    data object ClearFilterType : WorkoutEvent

}