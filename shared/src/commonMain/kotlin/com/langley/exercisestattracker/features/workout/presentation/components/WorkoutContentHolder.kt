package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState

@Composable
fun WorkoutContentHolder(
    modifier: Modifier = Modifier,
    workoutState: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit = {}
){

    // Workout Content
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        // Empty Workout View
        if (workoutState.exerciseList.isEmpty()){

            EmptyWorkoutContent(
                modifier = Modifier,
                workoutState = workoutState,
                onEvent = onEvent
            )

        }

        // Non-empty Workout View
        else {

            WorkoutContent(
                modifier = Modifier,
                workoutState = workoutState,
                onEvent = onEvent
            )
        }
    }

}