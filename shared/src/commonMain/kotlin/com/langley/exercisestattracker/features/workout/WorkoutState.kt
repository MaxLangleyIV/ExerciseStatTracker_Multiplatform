package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine

data class WorkoutState(

    val routine: ExerciseRoutine = ExerciseRoutine(),

    val exerciseQueue: List<ExerciseRecord> = listOf(),

    val completedExercises: List<ExerciseRecord> = listOf(),

    val exerciseMap: Map<String, List<ExerciseRecord>> = mapOf(),

    val exerciseSelectorVisible: Boolean = false



)