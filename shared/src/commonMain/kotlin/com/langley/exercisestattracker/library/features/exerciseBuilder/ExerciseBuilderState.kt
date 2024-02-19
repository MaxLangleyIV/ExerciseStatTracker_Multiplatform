package com.langley.exercisestattracker.library.features.exerciseBuilder

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.langley.exercisestattracker.core.domain.ExerciseDefinition

data class ExerciseBuilderState(

    // Body Region Toggles
    val upperBodySelected: Boolean = false,
    val lowerBodySelected: Boolean = false,
    val coreSelected: Boolean = false,
    val notApplicableSelected: Boolean = false,

    // Tags Section Visibility
    val tagsSectionVisible: Boolean = true,

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

sealed interface BodyRegion {
    data object Upper: BodyRegion
    data object Lower: BodyRegion
    data object Core: BodyRegion
    data object NotApplicable: BodyRegion
}