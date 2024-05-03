package com.langley.exercisestattracker.features.library

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule

data class LibraryState(

    // UI State
    val isShowingExercises: Boolean = true,
    val isShowingRoutines: Boolean = false,
    val isShowingSchedules: Boolean = false,

    //Data state.
    val exercises: List<ExerciseDefinition> = emptyList(),
    val selectedExercise: ExerciseDefinition? = null,

    val routines: List<ExerciseRoutine> = emptyList(),
    val selectedRoutine: ExerciseRoutine? = null,

    val schedules: List<ExerciseSchedule> = emptyList(),
    val selectedSchedule: ExerciseSchedule? = null,

    //Search state
    val isSearchDropdownOpen: Boolean = false,
    val searchString: String = "",
    val previousSearches: List<String>? = null,
    val searchFilterType: ExerciseLibraryFilterType? = null,

    //UI visibility flags.
    val isExerciseDetailsSheetOpen: Boolean = false,
    val isEditExerciseSheetOpen: Boolean = false,
    val isAddExerciseSheetOpen: Boolean = false,

    val isRoutineDetailsSheetOpen: Boolean = false,
    val isEditRoutineSheetOpen: Boolean = false,
    val isAddRoutineSheetOpen: Boolean = false,

    val isScheduleDetailsSheetOpen: Boolean = false,
    val isEditScheduleSheetOpen: Boolean = false,
    val isAddScheduleSheetOpen: Boolean = false,

    //Input validation errors state.
    val exerciseNameError: String? = null,
    val exerciseBodyRegionError: String? = null,
    val exerciseTargetMusclesError: String? = null,
)

sealed interface ExerciseLibraryFilterType {
    data class Favorite(val name: String = "favorite") : ExerciseLibraryFilterType
    data class UpperBody(val name: String = "upperBody") : ExerciseLibraryFilterType
    data class LowerBody(val name: String = "lowerBody") : ExerciseLibraryFilterType
    data class Barbell(val name: String = "barbell") : ExerciseLibraryFilterType
    data class Dumbbell(val name: String = "dumbbell") : ExerciseLibraryFilterType
    data class Calisthenic(val name: String = "calisthenic") : ExerciseLibraryFilterType
    data class Cardio(val name: String = "cardio") : ExerciseLibraryFilterType

}