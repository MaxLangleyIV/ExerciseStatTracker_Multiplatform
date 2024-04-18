package com.langley.exercisestattracker.features.library.routines

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import kotlinx.serialization.Serializable

@Serializable
data class RoutineBuilderState(
    val initialized: Boolean = false,

    val routine: ExerciseRoutine = ExerciseRoutine(),

    val exerciseList: List<ExerciseDefinition> = emptyList(),
    val recordList: List<ExerciseRecord> = emptyList(),

    val exerciseIdString: String = "",
    val repsString: String = "",

    // UI
    val isSelectorOpen: Boolean = false,

    //Input validation errors state.
    val nameError: String? = null,
)