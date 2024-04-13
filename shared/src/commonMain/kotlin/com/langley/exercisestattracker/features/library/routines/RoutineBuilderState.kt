package com.langley.exercisestattracker.features.library.routines

import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import kotlinx.serialization.Serializable

@Serializable
data class RoutineBuilderState(
    val initialized: Boolean = false,

    val routine: ExerciseRoutine = ExerciseRoutine(),

    //Input validation errors state.
    val nameError: String? = null,
)