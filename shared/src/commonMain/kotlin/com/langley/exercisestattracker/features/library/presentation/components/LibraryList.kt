package com.langley.exercisestattracker.features.library.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.presentation.components.exercises.ExerciseDefinitionListItem
import com.langley.exercisestattracker.features.library.presentation.components.routines.RoutineListItem
import com.langley.exercisestattracker.features.library.presentation.components.schedules.ScheduleListItem

@Composable
fun LibraryList(
    modifier: Modifier = Modifier,
    exercises: List<ExerciseDefinition>? = null,
    routines: List<ExerciseRoutine>? = null,
    schedules: List<ExerciseSchedule>? = null,
    onEvent: (LibraryEvent) -> Unit,
    focusManager: FocusManager,
    columns: GridCells = GridCells.Fixed(1)
){

    LazyVerticalGrid(
        columns = columns,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 8.dp),
    ){

        if (exercises != null){

            items(
                items = exercises,
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
        else if (routines != null){

            items(
                items = routines,
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
        else if (schedules != null){

            items(
                items = schedules,
                key = {item: ExerciseSchedule ->  item.exerciseScheduleId!!}
            )
            { schedule: ExerciseSchedule ->

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