package com.langley.exercisestattracker.features.exerciseBuilder

import com.langley.exercisestattracker.core.domain.ExerciseDefinition

data class ExerciseBuilderState(

    val initialized: Boolean = false,

    val newExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),

    val primaryTargetList: List<String>? = null,
    val musclesList: List<String>? = null,

    //Input validation errors state.
    val exerciseNameError: String? = null,
    val exerciseBodyRegionError: String? = null,
    val exerciseTargetMusclesError: String? = null

)