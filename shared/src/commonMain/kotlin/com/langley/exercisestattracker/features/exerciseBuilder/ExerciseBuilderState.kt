package com.langley.exercisestattracker.features.exerciseBuilder

data class ExerciseBuilderState(

    // Body Region Toggles
    val upperBodySelected: Boolean = false,
    val lowerBodySelected: Boolean = false,
    val coreSelected: Boolean = false,
    val fullBodySelected: Boolean = false,

    // Body Region Sub Group (Only for upper body as of now).
    val bodyRegion: BodyRegion? = null,
    val bodyRegionSubGroup: BodyRegionSubGroup? = null,

    // UI Visibility
    val targetMusclesVisible: Boolean = false,
    val tagsSectionVisible: Boolean = true,

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