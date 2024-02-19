package com.langley.exercisestattracker.library.features.exerciseBuilder

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class ExerciseBuilderState(
    //Intro Column Visibility
    val introColumnVisible: Boolean = true,

    // Main Exercise Builders
    val builderVisible: Boolean = false,
    val strengthBuilderVisible: Boolean = false,
    val cardioBuilderVisible: Boolean = false,

    // Builder View Options
    val upperBodySelected: Boolean = false,
    val lowerBodySelected: Boolean = false,
    val coreSelected: Boolean = false,

    // Tags Section Visibility
    val tagsSectionVisible: Boolean = false
)