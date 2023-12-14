package com.langley.exercisestattracker.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.langley.exercisestattracker.ui.theme.DarkColors
import com.langley.exercisestattracker.ui.theme.LightColors
import com.langley.exercisestattracker.ui.theme.Typography

@Composable
actual fun ExerciseStatTrackerTheme(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    content: @Composable () -> Unit
){
    val colorScheme = if (isDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}