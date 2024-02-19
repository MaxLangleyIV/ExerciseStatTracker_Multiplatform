package com.langley.exercisestattracker.library.features.exerciseBuilder

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.langley.exercisestattracker.core.domain.ExerciseDefinition

data class ExerciseBuilderState(
    // AddExerciseDef Visibility
    val isAddExerciseDefSheetOpen: Boolean = false,

    // Intro Column Visibility
    val introColumnVisible: Boolean = true,

    // Main Exercise Builders
    val builderVisible: Boolean = false,
    val strengthBuilderVisible: Boolean = false,
    val cardioBuilderVisible: Boolean = false,

    // Builder View Options
    val upperBodySelected: Boolean = false,
    val lowerBodySelected: Boolean = false,
    val coreSelected: Boolean = false,

    // Tags Section Visibility
    val tagsSectionVisible: Boolean = false,

    //Input validation errors state.
    val exerciseNameError: String? = null,
    val exerciseBodyRegionError: String? = null,
    val exerciseTargetMusclesError: String? = null
)

sealed interface ExerciseType {
    data object Strength: ExerciseType
    data object Cardio: ExerciseType
    data object Custom: ExerciseType

}