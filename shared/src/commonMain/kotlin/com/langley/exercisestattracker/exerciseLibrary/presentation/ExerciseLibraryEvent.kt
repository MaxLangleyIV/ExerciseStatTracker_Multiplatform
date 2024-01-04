package com.langley.exercisestattracker.exerciseLibrary.presentation

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition

sealed interface ExerciseLibraryEvent {

    data object DefaultEvent: ExerciseLibraryEvent
    data object CloseExerciseDetailsView : ExerciseLibraryEvent
    data class ExerciseDefinitionSelected(val exerciseDefinition: ExerciseDefinition): ExerciseLibraryEvent
    data class SaveExerciseDefinition(val exerciseDefinition: ExerciseDefinition): ExerciseLibraryEvent
}