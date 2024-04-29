package com.langley.exercisestattracker.features.library.routines.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.data.toBlankRecord
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.presentation.composables.ErrorDisplayingTextField
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.routines.RoutineBuilderEvent
import com.langley.exercisestattracker.features.library.routines.RoutineBuilderState
import com.langley.exercisestattracker.features.library.routines.RoutineBuilderViewModel
import com.langley.exercisestattracker.features.library.selector.SelectorView
import com.langley.exercisestattracker.features.workout.presentation.components.WorkoutContentHolder
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun RoutineEditView(
    dataSource: ExerciseAppDataSource,
    visible: Boolean,
    routine: ExerciseRoutine = ExerciseRoutine(),
    libraryEvent: (LibraryEvent) -> Unit,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 100),
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 100),
        )
    ){

        val routineBuilderViewModel = getViewModel(
            key = "routineBuilderViewModel",
            factory = viewModelFactory {
                RoutineBuilderViewModel(
                    dataSource = dataSource,
                    initialState = RoutineBuilderState(routine = routine),
                )
            }
        )

        val state by routineBuilderViewModel.state.collectAsState(RoutineBuilderState())

        LaunchedEffect(routine.exerciseRoutineId){
            routineBuilderViewModel.onEvent(RoutineBuilderEvent.UpdateSelectedRoutine(routine))
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) { focusManager.clearFocus() },
                verticalArrangement = Arrangement.SpaceBetween
            )
            {

                // Top Row
                Row(
                    modifier = Modifier.fillMaxWidth().weight(0.1F),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    IconButton(
                        onClick = {
                            libraryEvent(LibraryEvent.CloseEditView)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close"
                        )
                    }

                    Text(
                        text = "Routine Editor",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                    )

                    Text(
                        text = "Delete",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                routineBuilderViewModel.onEvent(
                                    RoutineBuilderEvent.DeleteRoutine(routine)
                                )
                            },
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )

                }

                // Body
                Column (
                    modifier = Modifier
                        .weight(0.85F)
                        .background(MaterialTheme.colorScheme.surface),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                )
                {

                    // Name Input
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        ErrorDisplayingTextField(
                            value = state.routine.routineName,
                            label = { Text(text = "Routine Name") },
                            placeholder = "Routine Name",
                            error = state.nameError,
                            onValueChanged = {
                                routineBuilderViewModel.onEvent(RoutineBuilderEvent.UpdateName(it))
                            },
                        )

                        Spacer(
                            Modifier.height(16.dp)
                        )

                        OutlinedTextField(
                            value = state.routine.description,
                            label = { Text(text = "Description") },
                            onValueChange = {
                                routineBuilderViewModel.onEvent(
                                    RoutineBuilderEvent.UpdateDescription(it)
                                )
                            },
                            placeholder = {
                                Text(text = "Exercise Description")
                            },
                            shape = RoundedCornerShape(20.dp)
                        )

                        Spacer(
                            Modifier.height(8.dp)
                        )

                    }

                    WorkoutContentHolder(
                        workoutMode = false,
                        exercises = state.exerciseList,
                        records = state.recordList,
                        openExerciseSelector = {
                            routineBuilderViewModel.onEvent(RoutineBuilderEvent.OpenSelector)
                        },
                        addToListOfRecords = {
                            routineBuilderViewModel.onEvent(RoutineBuilderEvent.AddRecords(it))
                        },
                        updateRepsFromString = {index, string ->
                            routineBuilderViewModel.onEvent(
                                RoutineBuilderEvent.UpdateRepsFromString(index, string)
                            )
                        },
                        removeRecord = {index ->
                            routineBuilderViewModel.onEvent(
                                RoutineBuilderEvent.RemoveRecord(index)
                            )
                        },
                        removeExercise = {index ->
                            routineBuilderViewModel.onEvent(
                                RoutineBuilderEvent.RemoveExercise(index)
                            )
                        }

                    )

                }

                // Bottom Bar
                Row(
                    modifier = Modifier.fillMaxWidth().weight(0.05F),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { libraryEvent(LibraryEvent.CloseEditView) }
                    ){
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            routineBuilderViewModel.onEvent(
                                RoutineBuilderEvent.InsertOrReplaceRoutine(state.routine)
                            )
                        }
                    ){
                        Text(text = "Update")
                    }

                }
            }

            SelectorView(
                visible = state.isSelectorOpen,
                modifier = Modifier.fillMaxSize(),
                dataSource = dataSource,
                onAddExercises = {list ->
                    for (exercise in list){
                        if (!state.exerciseList.contains(exercise)){
                            routineBuilderViewModel.onEvent(
                                RoutineBuilderEvent.AddToListOfExercises(listOf(exercise))
                            )

                            routineBuilderViewModel.onEvent(
                                RoutineBuilderEvent.AddRecords(
                                    listOf(exercise.toBlankRecord().copy(completed = false))
                                )
                            )
                        }
                    }
                },
                onClose = { routineBuilderViewModel.onEvent(RoutineBuilderEvent.CloseSelector) },
                showRoutinesTab = false,
                showSchedulesTab = false,
                focusManager = focusManager,
            )
        }
    }
}