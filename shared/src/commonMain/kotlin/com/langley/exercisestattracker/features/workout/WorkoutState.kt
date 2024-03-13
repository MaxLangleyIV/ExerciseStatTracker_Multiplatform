package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine

data class WorkoutState(

    val routine: ExerciseRoutine = ExerciseRoutine(),

    val exerciseQueue: List<ExerciseRecord> = mutableListOf(),

    val completedExercises: List<ExerciseRecord> = mutableListOf(),

    val exerciseMap: Map<String, List<ExerciseRecord>> = mapOf(),

    val exerciseSelectorVisible: Boolean = false



)