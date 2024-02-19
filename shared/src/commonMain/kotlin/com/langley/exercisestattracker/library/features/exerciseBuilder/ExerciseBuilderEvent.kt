package com.langley.exercisestattracker.library.features.exerciseBuilder

sealed interface ExerciseBuilderEvent {

    data class ExerciseTypeSelected(val exerciseType: ExerciseType) : ExerciseBuilderEvent
    data class OnNameChanged(val value: String) : ExerciseBuilderEvent
    data class OnBodyRegionChanged(val value: String) : ExerciseBuilderEvent
    data class OnTargetMusclesChanged(val value: String) : ExerciseBuilderEvent
    data class OnDescriptionChanged(val value: String) : ExerciseBuilderEvent
    data object ToggleIsWeighted : ExerciseBuilderEvent
    data object SaveOrUpdateDef : ExerciseBuilderEvent
    data object CloseAddDefClicked : ExerciseBuilderEvent
    data object DeleteDefinition : ExerciseBuilderEvent
    data object ToggleIsCalisthenics : ExerciseBuilderEvent
    data object ToggleIsCardio : ExerciseBuilderEvent
    data object ToggleHasReps : ExerciseBuilderEvent
    data object ToggleIsTimed : ExerciseBuilderEvent
    data object ToggleHasDistance : ExerciseBuilderEvent

}