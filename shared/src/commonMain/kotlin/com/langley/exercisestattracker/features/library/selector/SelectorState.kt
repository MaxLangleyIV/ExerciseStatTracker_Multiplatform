package com.langley.exercisestattracker.features.library.selector

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType

data class SelectorState(

    // Data State
    val exercises: List<ExerciseDefinition> = emptyList(),
    val routines: List<ExerciseRoutine> = emptyList(),
    val schedules: List<ExerciseSchedule> = emptyList(),

    val selectedExercises: List<ExerciseDefinition> = emptyList(),
    val selectedRoutine: ExerciseRoutine? = null,

    // UI State
    val isShowingExercises: Boolean = true,
    val isShowingRoutines: Boolean = false,
    val isShowingSchedules: Boolean = false,

    val dropdownExpanded: Boolean = false,
    val filterType: ExerciseLibraryFilterType? = null,
    val searchString: String = "",

) {
}