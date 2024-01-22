package com.langley.exercisestattracker.exerciseLibrary.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.AddNewExerciseDefView
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.EditExerciseDefDetailsView
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.ExerciseDefDetailsView
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.ExerciseDefinitionListItem
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.ExerciseLibraryTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseLibraryScreen(
    state: ExerciseLibraryState,
    newExerciseDefinition: ExerciseDefinition?,
    onEvent: (ExerciseLibraryEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
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
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            ExerciseLibraryTopBar(
                state = state,
                onEvent = onEvent
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
                                .clickable {
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