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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import com.langley.exercisestattracker.features.workout.WorkoutViewModel
import com.langley.exercisestattracker.features.workout.subfeature.ExerciseSelectorView
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

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

    val definitions by workoutViewModel.definitions.collectAsState(listOf())

    var isSingleExerciseMode by mutableStateOf( true )

    // Full Screen Container
    Surface {
        Column(
            modifier = modifier,
        ) {

            // Top Bar
            Column(
                modifier = Modifier.weight(0.1F)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    IconButton(
                        onClick = {
                            navController.navigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "Close"
                        )
                    }
                }
                // Routine Name
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(4.dp),

                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text(
                        text = state.routine.routineName.ifBlank {
                            "New Workout"
                        } + " - " + Clock.System.todayIn(TimeZone.currentSystemDefault())
                    )

                }
            }

            Spacer(Modifier.height(8.dp))

            // Workout Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .weight(0.8F)
                    .verticalScroll(rememberScrollState()),

                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                // Empty Workout View
                if (state.exerciseMap.isEmpty()){
                    Text(
                        text = "This workout is currently empty.",
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    // Buttons Column
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Add Exercise Button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 0.dp, max = 60.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    workoutViewModel.onEvent(WorkoutEvent.OpenExerciseSelector)
                                }
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.Center
                        ){

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1F),
                                text = "Add an exercise.",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.Center,
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        // Select Routine Button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 0.dp, max = 60.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {  }
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.Center,
                        ){

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1F),
                                text = "Select a routine.",
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                textAlign = TextAlign.Center,
                            )
                        }

                        Spacer(Modifier.height(8.dp))
                    }


                }
                // Non-empty Workout View
                else {

                    // Exercises
                    for (exercise in state.exerciseMap.keys){

                        // Exercise Group
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = exercise,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            // Exercise Sets
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                for ((index, set) in state.exerciseMap[exercise]!!.withIndex()){

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Column {
                                            Text(
                                                text = set.repsCompleted.toString(),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )

                                            Spacer(Modifier.height(4.dp))

                                            Text(
                                                text = set.repsCompleted.toString(),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }



                                }
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(
                                    onClick = {
                                        workoutViewModel.addToMap(state.exerciseMap[exercise]?.last())
                                    }
                                ){
                                    Text( text = "Add another set." )
                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }

                }


            }

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
                        onClick = {  }
                    ){
                        Text( text = "Cancel Session" )
                    }

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = {  }
                    ){
                        Text( text = "Save Session" )
                    }
                }
            }
        }

        // Exercise Selector
        ExerciseSelectorView(
            exerciseList = definitions,
            onEvent = workoutViewModel::onEvent,
            focusManager = focusManager,
            focusRequester = focusRequester,
            interactionSource = interactionSource,
            visible = state.exerciseSelectorVisible
        )


    }
}