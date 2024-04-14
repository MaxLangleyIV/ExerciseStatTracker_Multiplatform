package com.langley.exercisestattracker.features.workout.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.data.toBlankRecord
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheetNoScroll
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType
import com.langley.exercisestattracker.features.library.exercises.ExerciseDefinitionListItem
import com.langley.exercisestattracker.features.library.routines.RoutineBuilderEvent
import com.langley.exercisestattracker.features.library.schedules.ScheduleBuilderEvent
import com.langley.exercisestattracker.features.library.utils.filterDefinitionLibrary
import com.langley.exercisestattracker.features.records.RecordsFilterType
import com.langley.exercisestattracker.features.workout.WorkoutEvent

@Composable
fun ExerciseSelectorView(
    modifier: Modifier = Modifier,
    exerciseList: List<ExerciseDefinition> = listOf(),
    searchString: String = "",
    searchFilterType: ExerciseLibraryFilterType? = null,
    selectedExercises: List<ExerciseDefinition> = listOf(),
    workoutEvent: (WorkoutEvent) -> Unit,
    routineBuilderEvent: ((RoutineBuilderEvent) -> Unit)? = null,
    scheduleBuilderEvent: ((ScheduleBuilderEvent) -> Unit)? = null,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    visible: Boolean = false
    ) {

    BasicBottomSheetNoScroll(
        visible = visible,
        modifier = modifier
    ){

        var dropdownExpanded by remember { mutableStateOf(false) }
        var filterType: ExerciseLibraryFilterType? by remember { mutableStateOf(null) }
        var searchStringS by remember { mutableStateOf("") }
        var selectedExercises2: List<ExerciseDefinition> by remember { mutableStateOf(emptyList()) }


        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1F)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Back Button
            IconButton(
                onClick = {
                    if (workoutEvent != null){ workoutEvent(WorkoutEvent.CloseExerciseSelector) }
                    else if (routineBuilderEvent != null){
                        routineBuilderEvent(RoutineBuilderEvent.CloseExerciseSelector)
                    }
                    else if (scheduleBuilderEvent != null){
                        scheduleBuilderEvent(ScheduleBuilderEvent.CloseExerciseSelector)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "Close"
                )
            }

            // Search Bar
            OutlinedTextField(
                modifier = Modifier.focusRequester(FocusRequester())
                    .weight(0.5F),
                value = searchStringS,
                onValueChange = {
                                searchStringS = it
//                    if (workoutEvent != null){ workoutEvent(WorkoutEvent.OnSearchStringChanged(it)) }
//                    else if (routineBuilderEvent != null){
//                        routineBuilderEvent(RoutineBuilderEvent.OnSearchStringChanged(it))
//                    }
//                    else if (scheduleBuilderEvent != null){
//                        scheduleBuilderEvent(ScheduleBuilderEvent.OnSearchStringChanged(it))
//                    }
                },
                shape = RoundedCornerShape(20.dp),
                maxLines = 1
            )

            // Filter
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {

                Box {
                    IconButton(onClick = { dropdownExpanded = true }) {
                        Icon(
                            modifier = Modifier.size(400.dp),
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Filter",
                        )
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = {dropdownExpanded = false}
                    ){

                        DropdownMenuItem(
                            text = { Text("Favorite") },
                            onClick = {
                                dropdownExpanded = false
                                filterType = ExerciseLibraryFilterType.Favorite()
//                                workoutEvent(
//                                    WorkoutEvent
//                                        .SetCurrentFilterType(ExerciseLibraryFilterType.Favorite())
//                                )
                            }
                        )

                        Divider()

                        DropdownMenuItem(
                            text = { Text("Barbell") },
                            onClick = {
                                dropdownExpanded = false
                                filterType = ExerciseLibraryFilterType.Barbell()
//                                workoutEvent(
//                                    WorkoutEvent
//                                        .SetCurrentFilterType(ExerciseLibraryFilterType.Barbell())
//                                )
                            }
                        )

                        Divider()

                        DropdownMenuItem(
                            text = { Text("Dumbbell") },
                            onClick = {
                                dropdownExpanded = false
                                filterType = ExerciseLibraryFilterType.Dumbbell()
//                                workoutEvent(
//                                    WorkoutEvent
//                                        .SetCurrentFilterType(ExerciseLibraryFilterType.Dumbbell())
//                                )
                            }
                        )

                        Divider()

                        DropdownMenuItem(
                            text = { Text("Cardio") },
                            onClick = {
                                dropdownExpanded = false
                                filterType = ExerciseLibraryFilterType.Cardio()
//                                workoutEvent(
//                                    WorkoutEvent
//                                        .SetCurrentFilterType(ExerciseLibraryFilterType.Cardio())
//                                )
                            }
                        )

                        Divider()

                        DropdownMenuItem(
                            text = { Text("Calisthenics") },
                            onClick = {
                                dropdownExpanded = false
                                filterType = ExerciseLibraryFilterType.Calisthenic()
//                                workoutEvent(
//                                    WorkoutEvent
//                                        .SetCurrentFilterType(ExerciseLibraryFilterType.Calisthenic())
//                                )
                            }
                        )

                        Divider()

                        DropdownMenuItem(
                            text = { Text("None") },
                            onClick = {
                                dropdownExpanded = false
                                filterType = null
//                                workoutEvent(
//                                    WorkoutEvent.ClearFilterType
//                                )
                            }
                        )
                    }
                }
            }

            // Clear button
            if (searchStringS != "" || filterType != null) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                filterType = null
                                searchStringS = ""
//                                workoutEvent(
//                                    WorkoutEvent.ClearFilterType
//                                )
//                                workoutEvent(WorkoutEvent.OnSearchStringChanged(""))
                            },
                        text = "Clear",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
            }
        }

        // Exercise List
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(0.7F)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(vertical = 8.dp),
        ){
            items(
                items = filterDefinitionLibrary(
                    definitionLibrary = exerciseList,
                    filterType = filterType,
                    searchString = searchStringS
                ),
                key = { item: ExerciseDefinition ->  item.exerciseDefinitionId!! }
            ) { exerciseDefinition: ExerciseDefinition ->
                ExerciseDefinitionListItem(
                    exerciseDefinition,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp)
                        .focusable(true)
                        .clickable {
                            focusManager.clearFocus()
//                            workoutEvent(
//                                WorkoutEvent.DefinitionSelected(exerciseDefinition)
//                            )
                            selectedExercises2 = toggleDefInList(
                                exerciseDefinition,
                                selectedExercises2
                            )
                        },
                    selectable = true,
                    isClicked = selectedExercises2.contains(exerciseDefinition)
                )
            }
        }

        // Bottom Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1F)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    workoutEvent(WorkoutEvent.AddToListOfExercises(selectedExercises2))
                    for (exercise in selectedExercises2){
                        workoutEvent(
                            WorkoutEvent.AddToListOfRecords(
                                listOf( exercise.toBlankRecord().copy(completed = false) )
                            )
                        )
                    }
                    workoutEvent(WorkoutEvent.CloseExerciseSelector)
                }
            ){
                Text( text = "Add Exercise${if(selectedExercises2.size > 1){"s"}else{""}}" )
            }

            Button(
                onClick = {
                    workoutEvent(WorkoutEvent.CloseExerciseSelector)
                }
            ){
                Text( text = "Cancel" )
            }
        }

    } // End of screen containers

} // End of container wrapper.

private fun toggleDefInList(
    def: ExerciseDefinition,
    list: List<ExerciseDefinition>
): List<ExerciseDefinition>{

    val mutableList = list.toMutableList()

    if (mutableList.contains(def)){
        mutableList.remove(def)
    }
    else { mutableList.add(def) }

    return mutableList
}