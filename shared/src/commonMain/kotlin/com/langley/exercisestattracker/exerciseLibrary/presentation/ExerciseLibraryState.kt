package com.langley.exercisestattracker.exerciseLibrary.presentation

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionModel
import database.ExerciseDefinition

data class ExerciseLibraryState(
    val exerciseDefinitions: List<ExerciseDefinitionModel> = emptyList(),
    val selectedExerciseDefinition: ExerciseDefinitionModel? = null,
    val isSelectedExerciseDefSheetOpen: Boolean = false
)