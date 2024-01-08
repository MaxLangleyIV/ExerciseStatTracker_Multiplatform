package com.langley.exercisestattracker.exerciseLibrary.presentation

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition

data class ExerciseLibraryState(
    //Data state.
    val exerciseDefinitions: List<ExerciseDefinition> = emptyList(),
    val selectedExerciseDefinition: ExerciseDefinition? = null,

    //UI visibility flags.
    val isSelectedExerciseDefSheetOpen: Boolean = false,
    val isEditExerciseDefSheetOpen: Boolean = false,
    val isAddExerciseDefSheetOpen: Boolean = false,

    //Input validation errors state.
    val exerciseNameError: String? = null,
    val exerciseBodyRegionError: String? = null,
    val exerciseTargetMusclesError: String? = null,
)