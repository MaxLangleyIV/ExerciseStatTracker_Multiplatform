package com.langley.exercisestattracker.features.workout.subfeature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheetNoScroll
import com.langley.exercisestattracker.features.library.presentation.components.ExerciseDefinitionListItem
import com.langley.exercisestattracker.features.workout.WorkoutEvent

@Composable
fun ExerciseSelectorView(
    modifier: Modifier = Modifier,
    exerciseList: List<ExerciseDefinition> = listOf(),
    selectedExercises: List<ExerciseDefinition> = listOf(),
    onEvent: (WorkoutEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    visible: Boolean = false

    ) {

    BasicBottomSheetNoScroll(
        visible = visible,
        modifier = modifier
    ){
//    Column(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .focusable(true)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { focusManager.clearFocus() },
        ){

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(
                    onClick = {
                        onEvent(WorkoutEvent.CloseExerciseSelector)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "Close"
                    )
                }
            }
//        ExerciseLibraryTopBar(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(0.dp,16.dp),
//            state = libraryState,
//            onEvent = libraryViewModel::onEvent,
//            focusManager = focusManager,
//            navController = navController
//        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(vertical = 8.dp),

            content = {
                items(
                    items = exerciseList,
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
                                    WorkoutEvent.DefinitionSelected(exerciseDefinition)
                                )
                            },
                        selectable = true,
                        isClicked = selectedExercises.contains(exerciseDefinition)
                    )
                }
            }
        )
        }

    }

}