package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine

data class WorkoutState(

    val routine: ExerciseRoutine = ExerciseRoutine(),

    val exerciseQueue: List<ExerciseDefinition> = mutableListOf(),

    val completedExercises: List<ExerciseRecord> = mutableListOf()



)