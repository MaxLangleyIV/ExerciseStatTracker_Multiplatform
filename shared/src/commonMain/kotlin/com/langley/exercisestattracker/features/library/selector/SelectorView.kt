package com.langley.exercisestattracker.features.library.selector

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheetNoScroll
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
    visible: Boolean = false,
    showRoutinesTab: Boolean = true,
    showSchedulesTab: Boolean = true,
    startOnRoutinesTab: Boolean = false,
    startOnSchedulesTab: Boolean = false
    ) {

    BasicBottomSheetNoScroll(
        visible = visible,
        modifier = modifier
    ){

        val selectorViewModel = getViewModel(
            key = "selectorViewModel",
            factory = viewModelFactory {
                SelectorViewModel(
                    dataSource = dataSource,
                )
            }
        )
        val state by selectorViewModel.state.collectAsState(SelectorState())


        LaunchedEffect(startOnRoutinesTab){
            if (startOnRoutinesTab){
                selectorViewModel.selectRoutinesTab()
            }
        }


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

            showCloseButton = true,
            onClose = onClose,

            showSchedulesTab = showSchedulesTab,

            focusManager = focusManager,
        )

        // Exercise List
        if (state.isShowingExercises){
            LibraryList(
                modifier = Modifier
                    .weight(0.7F),
                exercises = filterDefinitionLibrary(
                    definitionLibrary = state.exercises,
                    filterType = state.filterType,
                    searchString = state.searchString
                ),
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
                modifier = Modifier
                    .weight(0.7F),
                routines = state.routines,
                routineOnClick = {
                    focusManager.clearFocus()
                    selectorViewModel.toggleSelectedRoutine(it)
                },
                focusManager = focusManager,
                selectable = true,
                selectedRoutine = state.selectedRoutine
            )
        }

        // Schedule List
        if (state.isShowingSchedules){
            LibraryList(
                modifier = Modifier
                    .weight(0.7F),
                schedules = state.schedules,
                scheduleOnClick = {
                    focusManager.clearFocus()
                    selectorViewModel.toggleSelectedSchedule(it)
                },
                focusManager = focusManager,
                selectable = true,
                selectedSchedule = state.selectedSchedule
            )
        }

        // Bottom Bar
        if (state.selectedExercises.isNotEmpty() || state.selectedRoutine != null){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.075F)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        onAddExercises(state.selectedExercises)
                        onAddRoutine(state.selectedRoutine)
                        onClose()
                    }
                ){
                    Text( text = "Add To Workout" )
                }

            }

        }


    } // End of BasicBottomSheet.

} // End of top level composable.
