package com.langley.exercisestattracker.features.library.features.exerciseBuilder

sealed interface ExerciseBuilderEvent {

    // UI Toggle Events
    data class ToggleBodyRegion(val bodyRegion: String): ExerciseBuilderEvent
    data class ToggleTargetMuscle(val value: String): ExerciseBuilderEvent
    data object ToggleIsCalisthenics : ExerciseBuilderEvent
    data object ToggleIsCardio : ExerciseBuilderEvent
    data object ToggleHasReps : ExerciseBuilderEvent
    data object ToggleIsTimed : ExerciseBuilderEvent
    data object ToggleHasDistance : ExerciseBuilderEvent

    data class OnNameChanged(val value: String) : ExerciseBuilderEvent
    data class OnDescriptionChanged(val value: String) : ExerciseBuilderEvent
    data object InitializeDefinition : ExerciseBuilderEvent

    data object ToggleIsWeighted : ExerciseBuilderEvent
    data object SaveOrUpdateDef : ExerciseBuilderEvent
    data object CloseAddDef : ExerciseBuilderEvent
    data object DeleteDefinition : ExerciseBuilderEvent

}