package com.langley.exercisestattracker.navigation

sealed interface Screen {
    data object Library: Screen
    data object Home: Screen
    data object Records: Screen
    data object ExerciseBuilder: Screen
}