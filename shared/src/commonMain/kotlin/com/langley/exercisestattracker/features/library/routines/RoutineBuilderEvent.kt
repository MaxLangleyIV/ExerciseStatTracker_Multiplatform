package com.langley.exercisestattracker.features.library.routines

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine

sealed interface RoutineBuilderEvent {
    data class OnSearchStringChanged(val value: String) : RoutineBuilderEvent
    data class AddToListOfExercises(val exercises: List<ExerciseDefinition>) : RoutineBuilderEvent
    data class AddRecords(val records: List<ExerciseRecord>) : RoutineBuilderEvent
    data class RemoveExercise(val index: Int) : RoutineBuilderEvent
    data object OpenSelector : RoutineBuilderEvent
    data object CloseSelector : RoutineBuilderEvent
    data class InsertOrReplaceRoutine(val routine: ExerciseRoutine) : RoutineBuilderEvent
    data class DeleteRoutine(val routine: ExerciseRoutine) : RoutineBuilderEvent
    data class UpdateRepsFromString(val index: Int, val string: String) : RoutineBuilderEvent
    data class RemoveRecord(val index: Int) : RoutineBuilderEvent
    data class UpdateName(val string: String) : RoutineBuilderEvent
    data class UpdateDescription(val string: String) : RoutineBuilderEvent
    data class UpdateSelectedRoutine(val routine: ExerciseRoutine) : RoutineBuilderEvent


}