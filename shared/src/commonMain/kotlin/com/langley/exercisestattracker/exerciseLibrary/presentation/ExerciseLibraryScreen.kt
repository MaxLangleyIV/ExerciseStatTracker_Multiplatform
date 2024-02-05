package com.langley.exercisestattracker.exerciseLibrary.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.AddNewExerciseDefView
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.EditExerciseDefDetailsView
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.ExerciseDefDetailsView
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.ExerciseDefinitionListItem
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.ExerciseLibraryTopBar

@Composable
fun ExerciseLibraryScreen(
    state: ExerciseLibraryState,
    newExerciseDefinition: ExerciseDefinition?,
    onEvent: (ExerciseLibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource
) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            if (
                !state.isEditExerciseDefSheetOpen
                and !state.isAddExerciseDefSheetOpen
                and !state.isExerciseDetailsSheetOpen
                ){

                FloatingActionButton(
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(ExerciseLibraryEvent.AddNewExerciseDefClicked)
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
                    .padding(0.dp,16.dp),
                state = state,
                onEvent = onEvent,
                focusManager = focusManager
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(vertical = 8.dp),

                content = {
                    items(state.exerciseDefinitions){ exerciseDefinition: ExerciseDefinition ->
                        ExerciseDefinitionListItem(
                            exerciseDefinition,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(8.dp)
                                .focusable(true)
                                .clickable {
                                    focusManager.clearFocus()
                                    onEvent(ExerciseLibraryEvent.ExerciseDefinitionSelected(exerciseDefinition))
                                },
                        )
                    }
                }
            )
        }

        ExerciseDefDetailsView(
            isVisible = state.isExerciseDetailsSheetOpen,
            onEvent = onEvent,
            selectedExerciseDefinition = state.selectedExerciseDefinition
        )

        EditExerciseDefDetailsView(
            isVisible = state.isEditExerciseDefSheetOpen,
            state = state,
            onEvent = onEvent,
            newExerciseDefinition = newExerciseDefinition
        )

        AddNewExerciseDefView(
            isVisible = state.isAddExerciseDefSheetOpen,
            state = state,
            onEvent = onEvent,
            newExerciseDefinition = newExerciseDefinition
        )
    }
}