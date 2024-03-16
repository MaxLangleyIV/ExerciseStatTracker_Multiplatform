package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine

data class WorkoutState(

    val routine: ExerciseRoutine = ExerciseRoutine(),

    val exerciseQueue: List<ExerciseRecord> = listOf(),

    val completedExercises: List<ExerciseRecord> = listOf(),

    val exerciseMap: Map<String, List<ExerciseRecord>> = mapOf(),

    val exerciseList: List<ExerciseDefinition> = listOf(),

    val recordsList: List<ExerciseRecord> = listOf(),

    val exerciseSelectorVisible: Boolean = false,

    val selectedExercises: List<ExerciseDefinition> = listOf()

)