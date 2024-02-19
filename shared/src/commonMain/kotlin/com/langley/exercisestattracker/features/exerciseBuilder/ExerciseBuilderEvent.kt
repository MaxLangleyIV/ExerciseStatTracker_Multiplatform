package com.langley.exercisestattracker.features.exerciseBuilder

sealed interface ExerciseBuilderEvent {

    // UI Toggle Events
    data class ToggleBodyRegion(val bodyRegion: BodyRegion): ExerciseBuilderEvent
    data class ToggleBodyRegionSubGroup(val subGroup: BodyRegionSubGroup): ExerciseBuilderEvent
    data class ToggleTargetMuscle(val value: String): ExerciseBuilderEvent
    data object ToggleIsCalisthenics : ExerciseBuilderEvent
    data object ToggleIsCardio : ExerciseBuilderEvent
    data object ToggleHasReps : ExerciseBuilderEvent
    data object ToggleIsTimed : ExerciseBuilderEvent
    data object ToggleHasDistance : ExerciseBuilderEvent

    data class OnNameChanged(val value: String) : ExerciseBuilderEvent
    data class OnBodyRegionChanged(val value: String) : ExerciseBuilderEvent
    data class OnTargetMusclesChanged(val value: String) : ExerciseBuilderEvent
    data class OnDescriptionChanged(val value: String) : ExerciseBuilderEvent
    data object ToggleIsWeighted : ExerciseBuilderEvent
    data object SaveOrUpdateDef : ExerciseBuilderEvent
    data object CloseAddDefClicked : ExerciseBuilderEvent
    data object DeleteDefinition : ExerciseBuilderEvent

}