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
import com.langley.exercisestattracker.features.library.presentation.components.LibraryTopBar
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
                    !libraryState.isEditExerciseSheetOpen
                    and !libraryState.isAddExerciseSheetOpen
                    and !libraryState.isExerciseDetailsSheetOpen
                    and !libraryState.isRoutineDetailsSheetOpen
                    and !libraryState.isEditRoutineSheetOpen
                ){

                    FloatingActionButton(
                        onClick = {
                            focusManager.clearFocus()

                            if (libraryState.isShowingExercises){
                                onEvent(LibraryEvent.ClearSelectedExercise)
                                onEvent(LibraryEvent.AddNewExercise)
                            }
                            else if (libraryState.isShowingRoutines){
                                onEvent(LibraryEvent.ClearSelectedRoutine)
                                onEvent(LibraryEvent.AddNewRoutine)
                            }

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
                LibraryTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(0.dp,4.dp),
                    searchString = libraryState.searchString,
                    onSearchStringChanged = {value: String ->
                        onEvent(LibraryEvent.OnSearchStringChanged(value))
                    },
                    filterType = libraryState.searchFilterType,
                    onFilterTypeChanged = {exerciseLibraryFilterType ->
                        onEvent(LibraryEvent.SetCurrentFilterType(exerciseLibraryFilterType))
                    },
                    isShowingExercises = libraryState.isShowingExercises,
                    isShowingRoutines = libraryState.isShowingRoutines,
                    isShowingSchedules = libraryState.isShowingSchedules,

                    onShowExercisesSelected = {onEvent(LibraryEvent.SelectExercisesTab)},
                    onShowRoutinesSelected = {onEvent(LibraryEvent.SelectRoutinesTab)},
//                    onShowSchedulesSelected = {onEvent(LibraryEvent.SelectSchedulesTab)},

                    onDefSelected = { onEvent(LibraryEvent.ExerciseSelected(it)) },
                    onRoutineSelected = { onEvent(LibraryEvent.RoutineSelected(it)) },
//                    onScheduleSelected = { onEvent(LibraryEvent.ScheduleSelected(it)) },
                    showSchedulesTab = false,

                    focusManager = focusManager,
                )

                // Exercise List
                if (libraryState.isShowingExercises){
                    LibraryList(
                        exercises = libraryState.exercises,
                        exerciseOnClick = { onEvent( LibraryEvent.ExerciseSelected(it) ) },
                        focusManager = focusManager,
                        columns = GridCells.Fixed(2)
                    )
                }

                // Routine List
                if (libraryState.isShowingRoutines){
                    LibraryList(
                        routines = libraryState.routines,
                        routineOnClick = { onEvent( LibraryEvent.RoutineSelected(it) ) },
                        focusManager = focusManager
                    )
                }

                // Schedule List
                if (libraryState.isShowingSchedules){
                    LibraryList(
                        schedules = libraryState.schedules,
                        scheduleOnClick = { onEvent( LibraryEvent.ScheduleSelected(it) ) },
                        focusManager = focusManager
                    )
                }
            }

            DefinitionDetailsView(
                isVisible = libraryState.isExerciseDetailsSheetOpen,
                libraryOnEvent = onEvent,
                definition =
                libraryState.selectedExercise ?: ExerciseDefinition()
            )


            ExerciseBuilderScreen(
                dataSource = dataSource,
                isVisible = libraryState.isAddExerciseSheetOpen,
                selectedExercise = libraryState.selectedExercise,
                libraryOnEvent = onEvent,
                focusManager = focusManager,
                interactionSource = interactionSource,
            )

            RoutineDetailsView(
                visible = libraryState.isRoutineDetailsSheetOpen,
                libraryOnEvent = onEvent,
                routine = libraryState.selectedRoutine ?: ExerciseRoutine()
            )

            RoutineEditView(
                visible = libraryState.isEditRoutineSheetOpen,
                dataSource = dataSource,
                routine = libraryState.selectedRoutine ?: ExerciseRoutine(),
                libraryEvent = onEvent,
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