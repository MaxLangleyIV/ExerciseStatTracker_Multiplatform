package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState

@Composable
fun WorkoutContentHolder(
    modifier: Modifier = Modifier,
//    workoutState: WorkoutState,
    exercises: List<ExerciseDefinition> = emptyList(),
    records: List<ExerciseRecord> = emptyList(),
//    onEvent: (WorkoutEvent) -> Unit = {},
    openExerciseSelector: () -> Unit = {},
    openRoutineSelector: () -> Unit = {},
    updateRepsFromString: (index: Int, string: String) -> Unit = { _, _ -> },
    updateWeightFromString: (index: Int, string: String) -> Unit = { _, _ -> },
    markSetComplete: (index: Int, set: ExerciseRecord) -> Unit = { _, _ -> },
    markSetIncomplete: (index: Int, set: ExerciseRecord) -> Unit = { _, _ -> },
    addToListOfRecords: (list: List<ExerciseRecord>) -> Unit = {},
    routineBuilderMode: Boolean = false
){

    // Workout Content
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        // Empty Workout View
        if (exercises.isEmpty()){

            EmptyWorkoutContent(
                modifier = Modifier,
                openExerciseSelector = openExerciseSelector,
                openRoutineSelector = openRoutineSelector
            )

        }

        // Non-empty Workout View
        else {

            WorkoutContent(
                modifier = Modifier.fillMaxSize(),
                exercises = exercises,
                records = records,
                openExerciseSelector = openExerciseSelector,
                updateRepsFromString = updateRepsFromString,
                updateWeightFromString = updateWeightFromString,
                markSetComplete = markSetComplete,
                markSetIncomplete = markSetIncomplete,
                addToListOfRecords = addToListOfRecords

            )

        }
    }

}