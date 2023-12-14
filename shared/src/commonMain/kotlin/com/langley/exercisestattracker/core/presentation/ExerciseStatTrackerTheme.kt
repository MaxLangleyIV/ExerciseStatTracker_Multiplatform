package com.langley.exercisestattracker.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun ExerciseStatTrackerTheme(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    content: @Composable () -> Unit
)