package com.langley.exercisestattracker.exerciseLibrary.presentation

data class ExerciseLibraryState(
    val exerciseDefinitions: List<com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition> = emptyList(),
    val selectedExerciseDefinition: com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition? = null,
    val isSelectedExerciseDefSheetOpen: Boolean = false
)