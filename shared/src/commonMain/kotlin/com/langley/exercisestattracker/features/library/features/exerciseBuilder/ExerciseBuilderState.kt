package com.langley.exercisestattracker.features.library.features.exerciseBuilder

import com.langley.exercisestattracker.core.domain.ExerciseDefinition

data class ExerciseBuilderState(
    val readyToInitialize: Boolean = false,
    val newExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),

    // Body Region (Sub Group only for upper body as of now).
    val bodyRegion: BodyRegion? = null,
    val bodyRegionSubGroup: BodyRegionSubGroup? = null,

    // Target  Muscles List
    val targetMusclesList: List<String>? = null,

    //Input validation errors state.
    val exerciseNameError: String? = null,
    val exerciseBodyRegionError: String? = null,
    val exerciseTargetMusclesError: String? = null
)


sealed interface BodyRegion {
    data object Upper: BodyRegion
    data object Lower: BodyRegion
    data object Core: BodyRegion
    data object Full: BodyRegion
    data object NotApplicable: BodyRegion
}
sealed interface BodyRegionSubGroup {
    data object Arms: BodyRegionSubGroup
    data object Back: BodyRegionSubGroup
    data object Chest: BodyRegionSubGroup
    data object Shoulders: BodyRegionSubGroup
    data object NotApplicable: BodyRegionSubGroup
}