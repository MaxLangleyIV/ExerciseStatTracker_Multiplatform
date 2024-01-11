package com.langley.exercisestattracker.exerciseLibrary.presentation

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition

data class ExerciseLibraryState(
    //Data state.
    val exerciseDefinitions: List<ExerciseDefinition> = emptyList(),
    val selectedExerciseDefinition: ExerciseDefinition? = null,

    //Search state
    val isSearching: Boolean = false,
    val searchString: String = "",

    //UI visibility flags.
    val isSelectedExerciseDefSheetOpen: Boolean = false,
    val isEditExerciseDefSheetOpen: Boolean = false,
    val isAddExerciseDefSheetOpen: Boolean = false,

    //Input validation errors state.
    val exerciseNameError: String? = null,
    val exerciseBodyRegionError: String? = null,
    val exerciseTargetMusclesError: String? = null,
)

sealed interface ExerciseLibraryFilterType {
    data object Favorite: ExerciseLibraryFilterType
    data object UpperBody: ExerciseLibraryFilterType
    data object LowerBody: ExerciseLibraryFilterType
    data object Barbell: ExerciseLibraryFilterType
    data object Dumbbell: ExerciseLibraryFilterType
    data object Calisthenic: ExerciseLibraryFilterType

}