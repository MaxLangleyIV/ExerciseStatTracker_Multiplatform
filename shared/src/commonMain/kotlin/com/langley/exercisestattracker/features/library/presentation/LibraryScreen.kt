package com.langley.exercisestattracker.features.library.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.langley.exercisestattracker.features.library.presentation.components.DefinitionDetailsView
import com.langley.exercisestattracker.features.library.presentation.components.ExerciseDefinitionListItem
import com.langley.exercisestattracker.features.library.presentation.components.ExerciseLibraryTopBar
import com.langley.exercisestattracker.features.library.presentation.components.RoutineListItem
import com.langley.exercisestattracker.features.library.presentation.components.ScheduleListItem
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
                ExerciseLibraryTopBar(
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
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .background(MaterialTheme.colorScheme.background),
                        contentPadding = PaddingValues(vertical = 8.dp),
                    ){
                        items(
                            items = libraryState.exercises,
                            key = {item: ExerciseDefinition ->  item.exerciseDefinitionId!!}
                        )
                        { exerciseDefinition: ExerciseDefinition ->
                            ExerciseDefinitionListItem(
                                exerciseDefinition,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(8.dp)
                                    .focusable(true)
                                    .clickable {
                                        focusManager.clearFocus()
                                        onEvent(
                                            LibraryEvent.DefinitionSelected(exerciseDefinition)
                                        )
                                    },
                            )
                        }
                    }
                }

                // Routine List
                if (libraryState.isShowingRoutines){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .background(MaterialTheme.colorScheme.background),
                        contentPadding = PaddingValues(vertical = 8.dp),
                    ){
                        items(
                            items = libraryState.routines,
                            key = {item: ExerciseRoutine ->  item.exerciseRoutineId!!}
                        )
                        { routine: ExerciseRoutine ->
                            RoutineListItem(
                                routine,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(8.dp)
                                    .focusable(true)
                                    .clickable {
                                        focusManager.clearFocus()
                                        onEvent(
                                            LibraryEvent.RoutineSelected(routine)
                                        )
                                    },
                            )
                        }
                    }
                }

                // Schedule List
                if (libraryState.isShowingSchedules){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .background(MaterialTheme.colorScheme.background),
                        contentPadding = PaddingValues(vertical = 8.dp),
                    ){
                        items(
                            items = libraryState.schedules,
                            key = {item: ExerciseSchedule ->  item.exerciseScheduleId!!}
                        )
                        { schedule: ExerciseSchedule ->
                            println("SCHEDULE: $schedule")
                            ScheduleListItem(
                                schedule,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(8.dp)
                                    .focusable(true)
                                    .clickable {
                                        focusManager.clearFocus()
                                        onEvent(
                                            LibraryEvent.ScheduleSelected(schedule)
                                        )
                                    },
                            )
                        }
                    }
                }
            }

            DefinitionDetailsView(
                isVisible = libraryState.isExerciseDetailsSheetOpen,
                libraryOnEvent = onEvent,
                selectedDefinition =
                libraryState.selectedExerciseDefinition?: ExerciseDefinition()
            )


            ExerciseBuilderScreen(
                dataSource = dataSource,
                isVisible = libraryState.isAddExerciseDefSheetOpen,
                selectedExercise = libraryState.selectedExerciseDefinition,
                libraryOnEvent = onEvent,
                focusManager = focusManager,
                interactionSource = interactionSource,
            )
        }

    }
}