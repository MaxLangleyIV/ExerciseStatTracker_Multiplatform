package com.langley.exercisestattracker.features.library.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.ExerciseBuilderScreen
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.LibraryState
import com.langley.exercisestattracker.features.library.exercises.DefinitionDetailsView
import com.langley.exercisestattracker.features.library.presentation.components.libraryTopBar
import com.langley.exercisestattracker.features.library.presentation.components.LibraryList
import com.langley.exercisestattracker.features.library.routines.views.RoutineDetailsView
import com.langley.exercisestattracker.features.library.routines.views.RoutineEditView
import com.langley.exercisestattracker.features.library.schedules.ScheduleDetailsView
import com.langley.exercisestattracker.navigation.ExerciseAppNavController

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    libraryState: LibraryState = LibraryState(),
    onEvent: (LibraryEvent) -> Unit = {},
    dataSource: ExerciseAppDataSource,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController,
    visible: Boolean = false
) {

    if (visible){

        Scaffold(
            modifier = modifier,
            floatingActionButton = {
                if (
                    !libraryState.isEditExerciseDefSheetOpen
                    and !libraryState.isAddExerciseDefSheetOpen
                    and !libraryState.isExerciseDetailsSheetOpen
                ){

                    FloatingActionButton(
                        onClick = {
                            focusManager.clearFocus()
                            onEvent(LibraryEvent.ClearSelectedDef)
                            onEvent(LibraryEvent.AddNewDefClicked)
                        },
                        shape = RoundedCornerShape(20.dp)
                    ){
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add exercise definition."
                        )
                    }
                }
            }
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .focusable(true)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) { focusManager.clearFocus() },
            ){
                libraryTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(0.dp,4.dp),
                    state = libraryState,
                    onEvent = onEvent,
                    focusManager = focusManager,
                    navController = navController
                )

                // Exercise List
                if (libraryState.isShowingExercises){
                    LibraryList(
                        exercises = libraryState.exercises,
                        onEvent = onEvent,
                        focusManager = focusManager,
                        columns = GridCells.Fixed(2)
                    )
                }

                // Routine List
                if (libraryState.isShowingRoutines){
                    LibraryList(
                        routines = libraryState.routines,
                        onEvent = onEvent,
                        focusManager = focusManager
                    )
                }

                // Schedule List
                if (libraryState.isShowingSchedules){
                    LibraryList(
                        schedules = libraryState.schedules,
                        onEvent = onEvent,
                        focusManager = focusManager
                    )
                }
            }

            DefinitionDetailsView(
                isVisible = libraryState.isExerciseDetailsSheetOpen,
                libraryOnEvent = onEvent,
                definition =
                libraryState.selectedExerciseDefinition ?: ExerciseDefinition()
            )


            ExerciseBuilderScreen(
                dataSource = dataSource,
                isVisible = libraryState.isAddExerciseDefSheetOpen,
                selectedExercise = libraryState.selectedExerciseDefinition,
                libraryOnEvent = onEvent,
                focusManager = focusManager,
                interactionSource = interactionSource,
            )

            RoutineDetailsView(
                isVisible = libraryState.isRoutineDetailsSheetOpen,
                libraryOnEvent = onEvent,
                routine = libraryState.selectedRoutine ?: ExerciseRoutine()
            )

            RoutineEditView(
                visible = libraryState.isEditRoutineSheetOpen,
                dataSource = dataSource,
                onEvent = onEvent,
                focusManager = focusManager,
                interactionSource = interactionSource
            )

            ScheduleDetailsView(
                isVisible = libraryState.isScheduleDetailsSheetOpen,
                libraryOnEvent = onEvent,
                schedule = libraryState.selectedSchedule ?: ExerciseSchedule()
            )

        }

    }
}