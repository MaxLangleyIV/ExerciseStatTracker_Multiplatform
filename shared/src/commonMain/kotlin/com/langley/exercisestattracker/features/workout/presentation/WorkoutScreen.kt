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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.library.selector.SelectorView
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import com.langley.exercisestattracker.features.workout.presentation.components.WorkoutTopBar
import com.langley.exercisestattracker.features.workout.presentation.components.WorkoutContentHolder
import com.langley.exercisestattracker.navigation.ExerciseAppNavController

@Composable
fun WorkoutScreen(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(8.dp),
    dataSource: ExerciseAppDataSource,
    workoutState: WorkoutState = WorkoutState(),
    onEvent: (WorkoutEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController,
    visible: Boolean = false
){

    if (visible){

        // Full Screen Container
        Surface {
            Column(
                modifier = modifier
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        focusManager.clearFocus()
                        onEvent(WorkoutEvent.ClearSelectedSet)
                    },
            ) {

                // Top Bar
                WorkoutTopBar(
                    modifier = Modifier.weight(0.1F),
                    navController = navController,
                    workoutState = workoutState,
                    onEvent = onEvent
                )

//                Spacer(Modifier.height(4.dp))

                // Content
                WorkoutContentHolder(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .weight(0.8F),

                    workoutState = workoutState,
                    onEvent = onEvent,
                )

                if (workoutState.exerciseList.isNotEmpty()){
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
                                onClick = { onEvent(WorkoutEvent.CancelWorkout) }
                            ){
                                Text( text = "Cancel Session" )
                            }

                            Spacer(Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    onEvent(WorkoutEvent.SaveWorkout)
                                }
                            ){
                                Text( text = "Save Session" )
                            }
                        }
                    }
                }
            }

            // Exercise Selector
            SelectorView(
                visible = workoutState.exerciseSelectorVisible,
                modifier = Modifier.fillMaxSize(),
                dataSource = dataSource,
                onAddExercises = { exercises ->
                    onEvent(WorkoutEvent.AddToExercisesWithDefaultSet(exercises))
                },
                onAddRoutine = { routine ->
                    if (routine != null){ onEvent(WorkoutEvent.AddRoutine(routine)) }
                },
                onClose = {onEvent(WorkoutEvent.CloseExerciseSelector)},
                focusManager = focusManager,
                focusRequester = focusRequester,
                interactionSource = interactionSource,
                showSchedulesTab = false,
                startOnRoutinesTab = workoutState.startSelectorOnRoutinesTab
            )

        }

    }

}