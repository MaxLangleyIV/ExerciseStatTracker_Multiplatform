package com.langley.exercisestattracker.exerciseLibrary.presentation

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition

sealed interface ExerciseLibraryEvent {

    data object DefaultEvent: ExerciseLibraryEvent
    data object CloseExerciseDetailsView : ExerciseLibraryEvent
    data object CloseEditExerciseDefView : ExerciseLibraryEvent
    data object SaveOrUpdateExerciseDef : ExerciseLibraryEvent
    data object AddNewExerciseDefClicked : ExerciseLibraryEvent
    data object CloseAddExerciseDefClicked : ExerciseLibraryEvent

    data class EditExerciseDefinition(val exerciseDefinition: ExerciseDefinition) : ExerciseLibraryEvent
    data class ExerciseDefinitionSelected(val exerciseDefinition: ExerciseDefinition): ExerciseLibraryEvent
    data class SaveExerciseDefinition(val exerciseDefinition: ExerciseDefinition): ExerciseLibraryEvent
    data class OnBodyRegionChanged(val value: String) : ExerciseLibraryEvent
    data class OnTargetMusclesChanged(val value: String) : ExerciseLibraryEvent
    data class OnExerciseNameChanged(val value: String) : ExerciseLibraryEvent
    data class OnExerciseDescriptionChanged(val value: String) : ExerciseLibraryEvent
    data class OnSearchStringChanged(val value: String) : ExerciseLibraryEvent
}