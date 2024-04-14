package com.langley.exercisestattracker.features.library.selector

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule

data class SelectorState(

    val exercises: List<ExerciseDefinition> = emptyList(),
    val routines: List<ExerciseRoutine> = emptyList(),
    val schedules: List<ExerciseSchedule> = emptyList(),

    val selectedExercises: List<ExerciseDefinition> = emptyList(),
    val selectedRoutines: List<ExerciseRoutine> = emptyList(),
    val selectedSchedules: List<ExerciseSchedule> = emptyList()

) {
}