package com.langley.exercisestattracker.features.library.selector

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheetNoScroll
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.exercises.ExerciseDefinitionListItem
import com.langley.exercisestattracker.features.library.presentation.components.LibraryList
import com.langley.exercisestattracker.features.library.presentation.components.LibraryTopBar
import com.langley.exercisestattracker.features.library.utils.filterDefinitionLibrary
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun SelectorView(
    modifier: Modifier = Modifier,
    dataSource: ExerciseAppDataSource,
    onAddExercises: (List<ExerciseDefinition>) -> Unit = {},
    onAddRoutine: (ExerciseRoutine?) -> Unit = {},
    onAddSchedule: (ExerciseSchedule?) -> Unit = {},
    onClose: () -> Unit = {},
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    visible: Boolean = false
    ) {

    BasicBottomSheetNoScroll(
        visible = visible,
        modifier = modifier
    ){

        val selectorViewModel = getViewModel(
            key = "selectorViewModel",
            factory = viewModelFactory {
                SelectorViewModel(dataSource = dataSource)
            }
        )
        val state by selectorViewModel.state.collectAsState(SelectorState())



        // Top Bar
        LibraryTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(0.dp,4.dp),

            searchString = state.searchString,
            onSearchStringChanged = {selectorViewModel.updateSearchString(it) },

            filterType = state.filterType,
            onFilterTypeChanged = {selectorViewModel.updateFilterType(it)},

            isShowingExercises = state.isShowingExercises,
            isShowingRoutines = state.isShowingRoutines,
            isShowingSchedules = state.isShowingSchedules,

            onShowExercisesSelected = {selectorViewModel.selectDefinitionsTab()},
            onShowRoutinesSelected = {selectorViewModel.selectRoutinesTab()},
            onShowSchedulesSelected = {selectorViewModel.selectSchedulesTab()},

            onDefSelected = { selectorViewModel.toggleSelectedDef(it) },
            onRoutineSelected = { selectorViewModel.toggleSelectedRoutine(it) },
            onScheduleSelected = { selectorViewModel.toggleSelectedSchedule(it) },

            focusManager = focusManager,
        )

//        // Exercise List
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier
//                .weight(0.7F)
//                .padding(8.dp)
//                .background(MaterialTheme.colorScheme.background),
//            contentPadding = PaddingValues(vertical = 8.dp),
//        ){
//            items(
//                items = filterDefinitionLibrary(
//                    definitionLibrary = state.exercises,
//                    filterType = state.filterType,
//                    searchString = state.searchString
//                ),
//                key = { item: ExerciseDefinition ->  item.exerciseDefinitionId!! }
//            ) { definition: ExerciseDefinition ->
//                ExerciseDefinitionListItem(
//                    definition,
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .padding(8.dp)
//                        .focusable(true)
//                        .clickable {
//                            focusManager.clearFocus()
//                            selectorViewModel.toggleSelectedDef(definition)
//                        },
//                    selectable = true,
//                    isClicked = state.selectedExercises.contains(definition)
//                )
//            }
//        }

        // Exercise List
        if (state.isShowingExercises){
            LibraryList(
                exercises = state.exercises,
                exerciseOnClick = {
                    focusManager.clearFocus()
                    selectorViewModel.toggleSelectedDef(it)
                },
                focusManager = focusManager,
                columns = GridCells.Fixed(2),
                selectable = true,
                selectedExercises = state.selectedExercises
            )
        }

        // Routine List
        if (state.isShowingRoutines){
            LibraryList(
                routines = state.routines,
                routineOnClick = {
                    focusManager.clearFocus()
                    selectorViewModel.toggleSelectedRoutine(it)
                },
                focusManager = focusManager,
                selectable = true,
                selectedRoutines = listOf(state.selectedRoutine?: ExerciseRoutine())
            )
        }

        // Schedule List
        if (state.isShowingSchedules){
            LibraryList(
                schedules = state.schedules,
                scheduleOnClick = {
                    focusManager.clearFocus()
                    selectorViewModel.toggleSelectedSchedule(it)
                },
                focusManager = focusManager,
                selectable = false
            )
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
                    onAddExercises(state.selectedExercises)
                    onAddRoutine(state.selectedRoutine)
                    onClose()
                }
            ){
                Text( text = "Add" )
            }

            Button(
                onClick = {
                    onClose()
                }
            ){
                Text( text = "Cancel" )
            }
        }

    } // End of screen containers

} // End of container wrapper.
