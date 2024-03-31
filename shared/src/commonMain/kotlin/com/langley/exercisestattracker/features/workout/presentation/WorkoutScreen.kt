package com.langley.exercisestattracker.features.workout.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import com.langley.exercisestattracker.features.workout.WorkoutViewModel
import com.langley.exercisestattracker.features.workout.presentation.components.TopBar
import com.langley.exercisestattracker.features.workout.presentation.components.WorkoutContentHolder
import com.langley.exercisestattracker.features.workout.subfeature.exerciseSelector.ExerciseSelectorView
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun WorkoutScreen(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(8.dp),
    dataSource: ExerciseAppDataSource,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController,
    visible: Boolean = true
){

    val workoutViewModel = getViewModel(
        key = "workoutViewModel",
        factory = viewModelFactory { WorkoutViewModel(dataSource) }
    )

    val state by workoutViewModel.state.collectAsState(WorkoutState())

    // Full Screen Container
    Surface {
        Column(
            modifier = modifier
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { focusManager.clearFocus() },
        ) {

            // Top Bar
            TopBar(
                modifier = Modifier.weight(0.1F),
                navController = navController,
                workoutState = state,
                onEvent = workoutViewModel::onEvent
            )

            Spacer(Modifier.height(8.dp))

            // Content
            WorkoutContentHolder(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .weight(0.8F),

                workoutState = state,
                onEvent = workoutViewModel::onEvent
            )

            // Save / Cancel Section
            Column(
                modifier = Modifier.weight(0.1F),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.navigateBack() }
                    ){
                        Text( text = "Cancel Session" )
                    }

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = {
                            workoutViewModel.onEvent(WorkoutEvent.SaveWorkout)
                            navController.navigateBack()
                        }
                    ){
                        Text( text = "Save Session" )
                    }
                }
            }
        }

        // Exercise Selector
        ExerciseSelectorView(
            modifier = Modifier.fillMaxSize(),
            exerciseList = state.exerciseLibrary,
            searchString = state.searchString,
            searchFilterType = state.searchFilter,
            selectedExercises = state.selectedExercises,
            onEvent = workoutViewModel::onEvent,
            focusManager = focusManager,
            focusRequester = focusRequester,
            interactionSource = interactionSource,
            visible = state.exerciseSelectorVisible
        )

    }
}